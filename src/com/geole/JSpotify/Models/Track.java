package com.geole.JSpotify.Models;

public class Track {
	
	private final SpotifyResource artist, album, track;
	
	private final String track_type;
	
	private final int length;
	
	public Track(SpotifyResource artist, SpotifyResource album, SpotifyResource track, String track_type, int length) {
		this.artist = artist;
		this.album = album;
		this.track = track;
		this.track_type = track_type;
		this.length = length;
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
