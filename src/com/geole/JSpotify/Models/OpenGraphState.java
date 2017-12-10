package com.geole.JSpotify.Models;

public class OpenGraphState {
	
	private final boolean posting_disabled, private_session;
	
	public OpenGraphState(boolean posting_disabled, boolean private_session) {
		this.posting_disabled = posting_disabled;
		this.private_session = private_session;
	}

	public boolean isPosting_disabled() {
		return posting_disabled;
	}

	public boolean isPrivate_session() {
		return private_session;
	}

}
