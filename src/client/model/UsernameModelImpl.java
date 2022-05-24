package client.model;

import client.network.LocalClient;

public class UsernameModelImpl implements UsernameModel {
	private final LocalClient client;
	private String username;

	public UsernameModelImpl(LocalClient client) {
		this.client = client;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public void setUsername(String username) {
		this.username = username;
	}
}
