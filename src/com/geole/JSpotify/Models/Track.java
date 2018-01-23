package com.geole.JSpotify.Models;

import org.json.JSONObject;

public class Track {
	
	private final SpotifyResource artist, album, track;
	
	private final String track_type;
	
	private final int length;

	public Track(JSONObject obj) {
		this.artist = new SpotifyResource(obj.optJSONObject("artist_resource"));
		this.album = new SpotifyResource(obj.optJSONObject("album_resource"));
		this.track = new SpotifyResource(obj.optJSONObject("track_resource"));
		this.track_type = obj.optString("track_type");
		this.length = obj.optInt("length");
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

	public String getTrackType() {
		return track_type;
	}

	public int getLength() {
		return length;
	}

}
