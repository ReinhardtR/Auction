package server.model.item.SaleStrategy;

import shared.SaleStrategyType;
import shared.network.model.Item;

import java.rmi.RemoteException;

public class AuctionStrategy implements SaleStrategy {
	private int currentBid = 0;
	private String currentBidder;

	@Override
	public void offer(Item item, int amount, String username) {
		currentBid = amount;
		currentBidder = username;

		try {
			item.getUpdateBroadcaster().broadcast("NEW_BID");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getBuyer() {
		return currentBidder;
	}

	@Override
	public int getOfferAmount() {
		return currentBid;
	}

	@Override
	public SaleStrategyType strategyType() {
		return SaleStrategyType.AUCTION;
	}
}
