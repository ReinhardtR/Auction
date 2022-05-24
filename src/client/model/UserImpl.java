package client.model;

import client.network.LocalClient;

public class UserImpl implements User {
	private final LocalClient client;
	private String username;

	public UserImpl(LocalClient client) {
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

	@Override
	public void createItem() {
		client.createItem();
	}

	@Override
	public void makeOfferOnItem(double offerAmount, ObservableItem item) {
		item.userSaleStrategy(offerAmount, username);
	}
}
