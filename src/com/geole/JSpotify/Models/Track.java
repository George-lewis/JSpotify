package com.geole.JSpotify.Models;

import org.json.JSONObject;

public class Track {
	
	private final SpotifyResource artist, album, track;
	
	private final String track_type;
	
	private final int length;

	public Track(JSONObject obj) {
		this.artist = new SpotifyResource(obj.getJSONObject("artist_resource"));
		this.album = new SpotifyResource(obj.getJSONObject("album_resource"));
		this.track = new SpotifyResource(obj.getJSONObject("track_resource"));
		this.track_type = obj.getString("track_type");
		this.length = obj.getInt("length");
	}
	
	public boolean isAd() {
		return track_type == "ad" || length == 0;
	}

	public SpotifyResource getArtist() {
		return artist;
	}

	public SpotifyResource getAlbum() {
		return album;
	}

	public SpotifyResource getTrack() {
		return track;
	}

	public String getTrack_type() {
		return track_type;
	}

	public int getLength() {
		return length;
	}

}
