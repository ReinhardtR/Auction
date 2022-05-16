package server.model.item.SaleStrategy;

import server.model.item.Item;
import shared.EventType;
import shared.SaleStrategyType;

import java.rmi.RemoteException;
import java.time.temporal.Temporal;

public class BuyoutStrategy implements SaleStrategy {
	private double price;
	private String buyer;

	public BuyoutStrategy(int price) {
		this.price = price;
	}

	@Override
	public void offer(Item item, double amount, String username) {
		price = amount;
		buyer = username;

		try {
			item.setAsSold();
			item.getUpdateBroadcaster().broadcastEventForItem(EventType.ITEM_SOLD.toString(), item.getItemID());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getBuyer() {
		return buyer;
	}

	@Override
	public double getOfferAmount() {
		return price;
	}

	@Override
	public SaleStrategyType strategyType() {
		return SaleStrategyType.BUYOUT;
	}


	@Override
	public Temporal getEndTime() {
		return null;
	}
}
