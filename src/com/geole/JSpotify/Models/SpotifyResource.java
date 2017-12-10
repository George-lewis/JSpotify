package com.geole.JSpotify.Models;

import org.json.JSONObject;

public class SpotifyResource {
	
	private final String name, location, uri;
	
	public SpotifyResource(JSONObject obj) {
		this.name = obj.getString("name");
		this.location = obj.getJSONObject("location").getString("og");
		this.uri = obj.getString("uri");
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
