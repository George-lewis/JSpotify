package com.geole.JSpotify.Models;

import org.json.JSONObject;

public class OpenGraphState {
	
	private final boolean posting_disabled, private_session;
	
	public OpenGraphState(JSONObject obj) {
		this.posting_disabled = obj.getBoolean("posting_disabled");
		this.private_session = obj.getBoolean("private_session");
	}

	public boolean isPostingDisabled() {
		return posting_disabled;
	}

	public boolean isPrivateSession() {
		return private_session;
	}

}
