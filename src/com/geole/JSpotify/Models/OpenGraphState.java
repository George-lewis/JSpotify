package com.geole.JSpotify.Models;

import org.json.JSONObject;

public class OpenGraphState {
	
	private final boolean posting_disabled, private_session;
	
	public OpenGraphState(JSONObject obj) {
		this.posting_disabled = obj.getBoolean("posting_disabled");
		this.private_session = obj.getBoolean("private_session");
	}

	public boolean isPosting_disabled() {
		return posting_disabled;
	}

	public boolean isPrivate_session() {
		return private_session;
	}

}
