package com.geole.JSpotify.Models;

public class Status {
	
	private final long server_time;
	
	private final int playing_position, version, volume;
	
	private final boolean prev_enabled, play_enabled, next_enabled, running, 
						repeat, online, playing, shuffle;
	
	private final String client_version;
	
	private final OpenGraphState OPS;
	
	private final Track track;
	
	public Status(long server_time, int playing_position, boolean prev_enabled,
			boolean play_enabled, boolean next_enabled, int version,
			int volume, boolean running, boolean repeat,
			boolean online, boolean playing, OpenGraphState OPS,
			String client_version, boolean shuffle, Track track) {
		
		this.server_time = server_time;
		this.playing_position = playing_position;
		this.prev_enabled = prev_enabled;
		this.play_enabled = play_enabled;
		this.next_enabled = next_enabled;
		this.version = version;
		this.volume = volume;
		this.running = running;
		this.repeat = repeat;
		this.online = online;
		this.playing = playing;
		this.OPS = OPS;
		this.client_version = client_version;
		this.shuffle = shuffle;
		this.track = track;
		
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
