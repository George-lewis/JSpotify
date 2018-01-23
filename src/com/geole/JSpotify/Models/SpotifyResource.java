package com.geole.JSpotify.Models;

import org.json.JSONObject;

public class SpotifyResource {
	
	public static final JSONObject EMPTY_JSON = new JSONObject();
	
	private final String name, location, uri;
	
	public SpotifyResource(JSONObject obj) {
		if (obj == null) {
			obj = EMPTY_JSON;
		}
		this.name = obj.optString("name");
		this.location = obj.optJSONObject("location") != null ? obj.getJSONObject("location").getString("og") : null;
		this.uri = obj.optString("uri");
	}

	public String getName() {
		return name;
	}

	public String getLocation() {
		return location;
	}

	public String getUri() {
		return uri;
	}

}
