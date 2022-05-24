package client.model;

import client.network.LocalClient;
import shared.SaleStrategyType;

import java.rmi.RemoteException;

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
	public void createItem(String title, String description, String tags, SaleStrategyType saleType, double offer, String endtime) {
		try {
			client.createItem(title, description, tags, saleType, username, offer, endtime);
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void makeOfferOnItem(double offerAmount, ObservableItem item) {
		item.userSaleStrategy(offerAmount, username);
	}
}
