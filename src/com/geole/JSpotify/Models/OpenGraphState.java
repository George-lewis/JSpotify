package com.geole.JSpotify.Models;

import org.json.JSONObject;

public class OpenGraphState {
	
	private final boolean posting_disabled, private_session;
	
	public OpenGraphState(JSONObject obj) {
		// Not always present
		this.posting_disabled = obj.optBoolean("posting_disabled", false);
		this.private_session = obj.getBoolean("private_session");
	}

	public boolean isPostingDisabled() {
		return posting_disabled;
	}

	public boolean isPrivateSession() {
		return private_session;
	}

}
