package server.model.item.SaleStrategy;

import shared.network.model.Item;

public class AuctionStrategy implements SaleStrategy {
	private int currentBid = 0;
	private String currentBidder;

	@Override
	public void offer(Item item, int amount, String username) {
		currentBid = amount;
		currentBidder = username;
	}

	@Override
	public String getBuyer() {
		return currentBidder;
	}

	@Override
	public int getOfferAmount() {
		return currentBid;
	}
}
