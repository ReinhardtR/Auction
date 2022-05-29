package client.model;

import client.model.item.ObservableItem;
import client.network.LocalClient;
import shared.SaleStrategyType;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalTime;

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
	public void createItem(String title, String description, String tags, SaleStrategyType saleType, double offer, LocalTime endtime, LocalDate endDate) {
		try {
			client.createItem(title, description, tags, saleType, username, offer, endtime,endDate);
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void makeOfferOnItem(double offerAmount, ObservableItem item) {
		item.userSaleStrategy(offerAmount, username);
	}
}
