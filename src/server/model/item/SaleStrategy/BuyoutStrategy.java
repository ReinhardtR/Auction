package server.model.item.SaleStrategy;

import shared.network.model.Item;

import java.rmi.RemoteException;

public class BuyoutStrategy implements SaleStrategy {
	private int price;
	private String buyer;

	@Override
	public void offer(Item item, int amount, String username) {
		price = amount;
		buyer = username;

		try {
			item.setAsSold();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getBuyer() {
		return buyer;
	}

	@Override
	public int getOfferAmount() {
		return price;
	}

	@Override
	public String strategyType() {
		return "Buyout";
	}


}
