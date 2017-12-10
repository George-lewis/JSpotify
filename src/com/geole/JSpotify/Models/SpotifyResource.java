package com.geole.JSpotify.Models;

public class SpotifyResource {
	
	private final String name, location, uri;
	
	public SpotifyResource(String name, String location, String uri) {
		this.name = name;
		this.location = location;
		this.uri = uri;
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
