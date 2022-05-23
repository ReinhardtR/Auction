package server.model.item.SaleStrategy;

import server.model.item.Cart;
import server.model.item.Item;
import shared.EventType;
import shared.SaleStrategyType;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;

public class AuctionStrategy implements SaleStrategy {
	private final Temporal endTimestamp;
	private double currentBid;
	private String currentBidder;

	public AuctionStrategy(double currentBid, String bidder, LocalDateTime endTimestamp) {
		this.currentBid = currentBid;
		this.currentBidder = bidder;
		this.endTimestamp = endTimestamp;
	}

	@Override
	public void offer(Item item, double amount, String username) {
		currentBid = amount;
		currentBidder = username;

		try {
			item.getUpdateBroadcaster().broadcastEventForItem(EventType.NEW_BID.toString(), item.getItemID());
			Cart.getInstance().updateItemOffer(item);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getBuyer() {
		return currentBidder;
	}

	@Override
	public double getOfferAmount() {
		return currentBid;
	}

	@Override
	public SaleStrategyType strategyType() {
		return SaleStrategyType.AUCTION;
	}

	@Override
	public Temporal getEndTime() {
		return endTimestamp;
	}
}
