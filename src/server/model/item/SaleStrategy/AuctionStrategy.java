package server.model.item.SaleStrategy;

import shared.EventType;
import shared.SaleStrategyType;
import server.model.item.Item;

import java.rmi.RemoteException;

public class AuctionStrategy implements SaleStrategy {
	private int currentBid;
	private String currentBidder;

	public AuctionStrategy(int currentBid)
	{
		this.currentBid = currentBid;
	}

	@Override
	public void offer(Item item, int amount, String username) {
		currentBid = amount;
		currentBidder = username;

		try {
			item.getUpdateBroadcaster().broadcastEventForItem(EventType.NEW_BID.toString(), item.getItemID());
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
