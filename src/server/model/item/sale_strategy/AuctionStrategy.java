package server.model.item.sale_strategy;

import server.service.customer.CustomerItemService;
import shared.SaleStrategyType;
import shared.network.model.Item;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
		// todo: add offer validation
		try {
			// Pas callback func to make sure the DB is updated before updating state
			CustomerItemService.getInstance().updateItemOffer(item, () -> {
				currentBid = amount;
				currentBidder = username;
			});
		} catch (SQLException | RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getBuyer() {
		return currentBidder;
	}

	@Override
	public boolean getIsSold() {
		return endTimestamp.until(LocalDateTime.now(), ChronoUnit.SECONDS) <= 0;
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