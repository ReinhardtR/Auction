package server.model.auctionHouseModel;

import shared.network.model.Item;

public class ItemImpl implements Item {
	private final String itemID;

	public ItemImpl(String itemID) {
		this.itemID = itemID;
	}

	@Override
	public String getItemID() {
		return itemID;
	}

	@Override
	public void userSaleStrategy(int amount, String username) {
		System.out.println("STRATEGY TRIGGERED");
	}
}
