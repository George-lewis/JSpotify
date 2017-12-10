package com.geole.JSpotify.Models;

import org.json.JSONObject;

public class Status {
	
	private final long server_time;
	
	private final int playing_position, version, volume;
	
	private final boolean prev_enabled, play_enabled, next_enabled, running, 
						repeat, online, playing, shuffle;
	
	private final String client_version;
	
	private final OpenGraphState OPS;
	
	private final Track track;
	
	public Status(JSONObject obj) {
		this.server_time = obj.getLong("server_time");
		this.playing_position = obj.getInt("playing_position");
		this.prev_enabled = obj.getBoolean("prev_enabled");
		this.play_enabled = obj.getBoolean("play_enabled");
		this.next_enabled = obj.getBoolean("next_enabled");
		this.version = obj.getInt("version");
		this.volume = obj.getInt("volume");
		this.running = obj.getBoolean("running");
		this.repeat = obj.getBoolean("repeat");
		this.online = obj.getBoolean("repeat");
		this.playing = obj.getBoolean("playing");
		this.OPS = new OpenGraphState(obj.getJSONObject("open_graph_state"));
		this.client_version = obj.getString("client_version");
		this.shuffle = obj.getBoolean("shuffle");
		this.track = new Track(obj.getJSONObject("track"));
	}

	public long getServerTime() {
		return server_time;
	}

	public int getPlayingPosition() {
		return playing_position;
	}

	public int getVersion() {
		return version;
	}

	public int getVolume() {
		return volume;
	}

	public boolean isPrevEnabled() {
		return prev_enabled;
	}

	public boolean isPlayEnabled() {
		return play_enabled;
	}

	public boolean isNextEnabled() {
		return next_enabled;
	}

	public boolean isRunning() {
		return running;
	}

	public boolean isRepeat() {
		return repeat;
	}

	public boolean isOnline() {
		return online;
	}

	public boolean isPlaying() {
		return playing;
	}

	public boolean isShuffle() {
		return shuffle;
	}

	public String getClientVersion() {
		return client_version;
	}

	public OpenGraphState getOPS() {
		return OPS;
	}

	public Track getTrack() {
		return track;
	}

}
