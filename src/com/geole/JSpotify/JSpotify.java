package com.geole.JSpotify;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.json.JSONException;
import org.json.JSONObject;

import com.geole.JSpotify.Models.Status;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class JSpotify {

	/**
	 * A SpotifyException is thrown when the Spotify api returns an error or there is an issue accessing the api
	 */
	public static class SpotifyException extends Exception {

		private static final long serialVersionUID = 7267790627241968134L;

		private Optional<Integer> errorCode;
		
		private Optional<String> errorMessage;
		
		public SpotifyException(String message) {
			super(message);
			this.errorCode = Optional.empty();
			this.errorMessage = Optional.empty();
		}
	
		public SpotifyException(String message, Throwable throwable) {
			super(message, throwable);
			this.errorCode = Optional.empty();
			this.errorMessage = Optional.empty();
		}
		
		public SpotifyException(String message, int error) {
			super(message);
			this.errorCode = Optional.of(error);
			this.errorMessage = Optional.ofNullable(Errors.getOrDefault(error, null));
		}
	
		public SpotifyException(String message, Throwable throwable, int error) {
			super(message, throwable);
			this.errorCode = Optional.of(error);
			this.errorMessage = Optional.ofNullable(Errors.getOrDefault(error, null));
		}
		
		/**
		 * @return true if the error is returned by the api and false if the error arose from accessing the api
		 */
		public boolean isAPIError() {
			return errorCode.isPresent();
		}
		
		public Optional<Integer> getErrorCode() {
			return this.errorCode;
		}
		
		public Optional<String> getErrorMessage() {
			return this.errorMessage;
		}

	}

	public static final String VERSION = "v0.1";

	private static final Map<String, Object> EMPTY_MAP = Collections.emptyMap();

	private static String OAuthToken, CSRFToken;

	private static String baseURL = "http://JSpotify.spotilocal.com:{port}";

	private static boolean initialized = false;

	// Maps error codes to error messages
	// Sourced from https://github.com/chrippa/spotify-remote
	private static final HashMap<Integer, String> Errors = new HashMap<>();

	static {

		// Static initialization of map
		Errors.put(4001, "Unknown method");
		Errors.put(4002, "Error parsing request");
		Errors.put(4003, "Unknown service");
		Errors.put(4004, "Service not responding");
		Errors.put(4102, "Invalid OAuthToken");
		Errors.put(4103, "Expired OAuth token");
		Errors.put(4104, "OAuth token not verified");
		Errors.put(4105, "Token verification denied, too many requests");
		Errors.put(4106, "Token verification timeout");
		Errors.put(4107, "Invalid Csrf token");
		Errors.put(4108, "OAuth token is invalid for current user");
		Errors.put(4109, "Invalid Csrf path");
		Errors.put(4110, "No user logged in");
		Errors.put(4111, "Invalid scope");
		Errors.put(4112, "Csrf challenge failed");
		Errors.put(4201, "Upgrade to premium");
		Errors.put(4202, "Upgrade to premium or wait");
		Errors.put(4203, "Billing failed");
		Errors.put(4204, "Technical error");
		Errors.put(4205, "Commercial is playing");
		Errors.put(4301, "Content is unavailable but can be purchased");
		Errors.put(4302, "Premium only content");
		Errors.put(4303, "Content unavailable");

	}

	// This is a static class
	private JSpotify() {}

	/**
	 * Initializes the api for use
	 * Do not call any api methods until you have initialized
	 * This function call is blocking and may take some time to complete
	 * NOTE: On a successful initialization true is returned, however
	 * should an error occur, false is not returned and instead a @SpotifyException
	 * is thrown
	 * 
	 * @return Returns true if initialization was successful
	 * @throws SpotifyException
	 *             Thrown when the api fails to initialize this can be a failure to
	 *             acquire the OAuth Token, CSRF Token, or local port
	 */
	public static boolean initialize() throws SpotifyException {

		// Acquire OAuth token from static url and cache result
		while (true) {
			try {
				HttpResponse<String> resp = Unirest.get("https://open.spotify.com/token").asString();
				if (resp.getStatus() == 503) {
					continue;
				}
				JSpotify.OAuthToken = new JSONObject(resp.getBody()).getString("t");
			} catch (JSONException | UnirestException e) {
				throw new SpotifyException("Failed to acquire OAuth token", e);
			}
			break;
		}

		ExecutorService executor = Executors.newFixedThreadPool(30);

		ArrayList<Future<String>> futures = new ArrayList<>(30);

		for (int i = 4370; i <= 4400; i++) {
			final String strurl = JSpotify.baseURL.replace("{port}", Integer.toString(i));
			Future<String> future = executor.submit(() -> {
				try {
					URL url = new URL(strurl);
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					connection.connect();
				} catch (IOException e) {
					return null;
				}
				return strurl;
			});
			futures.add(future);
		}

		while (true) {

			Optional<String> opt = futures.stream().filter(f -> {
				try {
					return f.isDone() && Objects.nonNull(f.get());
				} catch (InterruptedException | ExecutionException e) {
					return true;
				}
			}).map(f -> {
				try {
					return f.get();
				} catch (InterruptedException | ExecutionException e) {
					return null; // This case was eliminated in the filter block
				}
			}).filter(url -> {
				try {
					Unirest.get(url + "/remote/status.json").queryString("oauth", OAuthToken)
							.queryString("csrf", CSRFToken).asJson().getBody();
				} catch (UnirestException e) {
					return false;
				}
				return true;
			}).findFirst();

			if (opt.isPresent()) {
				executor.shutdownNow();
				opt.ifPresent(url -> JSpotify.baseURL = url);
				break;
			}
			
			if (futures.stream().allMatch(f -> f.isDone())) {
				throw new SpotifyException("Unable to resolve Spotify port. Is Spotify running?");
			}

		}

		try {

			JSONObject obj = Unirest.get(baseURL + "/simplecsrf/token.json")
					.header("Origin", "https://open.spotify.com").asJson().getBody().getObject();

			if (obj.has("error")) {
				JSONObject error = obj.getJSONObject("error");
				throw new SpotifyException("Spotify client returned an error!\nError code: " + error.getString("type")
						+ "\nMessage: " + error.getString("message"));
			}

			JSpotify.CSRFToken = obj.getString("token");
		} catch (JSONException | UnirestException e) {
			throw new SpotifyException("Failed to acquire CSRF token.", e);
		}

		JSpotify.initialized = true;

		return true;

	}

	private static <C> C request(String path, Map<String, Object> opts, Class<C> clazz) throws SpotifyException {

		if (!initialized) {
			throw new SpotifyException("JSpotify must be initialized before you can use it.");
		}
		
		try {

			JSONObject obj = Unirest.get(baseURL + path).queryString("oauth", OAuthToken).queryString("csrf", CSRFToken)
					.queryString(opts).asJson().getBody().getObject();

			if (obj.has("error")) {
				JSONObject error = obj.getJSONObject("error");
				String error_code = error.getString("type");
				String message = error.has("message") ? error.getString("message")
						: Errors.get(Integer.parseInt(error_code));
				throw new SpotifyException(
						"Spotify client returned an error!\nError code " + error_code + "\nMessage: " + message);
			}

			if (clazz.equals(Void.TYPE)) {
				return null;
			} else {
				return clazz.getConstructor(JSONObject.class).newInstance(obj);
			}

		} catch (UnirestException | JSONException e) {
			throw new SpotifyException("Failed to perform api call.", e);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new SpotifyException("Failed to instantiate the model class during api call.", e);
		}

	}

	public static String getOAuthToken() {
		return JSpotify.OAuthToken;
	}

	public static String getCSRFToken() {
		return JSpotify.CSRFToken;
	}

	public static boolean isInitialized() {
		return JSpotify.initialized;
	}

	public static String getBaseURL() {
		return JSpotify.baseURL;
	}

	/*
	 * Seems to not work - additional testing required public static void
	 * enque(String... urls) throws SpotifyException { for (String url : urls) {
	 * JSpotify.request("/remote/play.json", Map.of("uri", url, "action", "queue"),
	 * Void.TYPE); } }
	 */

	/**
	 * Request a song to be played
	 * @param url Any valid Spotify URL to be played
	 * @return A fresh status from right after the song has been requested
	 */
	public static Status play(String url) throws SpotifyException {
		return JSpotify.request("/remote/play.json", Map.of("uri", url), Status.class);
	}

	/**
	 * Pause the currently playing music
	 * @return A fresh status from right after the song has been paused
	 */
	public static Status pause() throws SpotifyException {
		return JSpotify.request("/remote/pause.json", EMPTY_MAP, Status.class);
	}

	/**
	 * Unpauses the music
	 * @return A fresh status from right after the song has been unpaused
	 */
	public static Status unpause() throws SpotifyException {
		return JSpotify.request("/remote/pause.json", EMPTY_MAP, Status.class);
	}

	/**
	 * Retrieves the current status of the client
	 * @return The status of the client
	 */
	public static Status getStatus() throws SpotifyException {
		return JSpotify.request("/remote/status.json", EMPTY_MAP, Status.class);
	}

}
