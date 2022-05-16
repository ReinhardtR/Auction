package server.model.item.SaleStrategy;

import shared.EventType;
import shared.SaleStrategyType;
import server.model.item.Item;

import java.rmi.RemoteException;

public class BuyoutStrategy implements SaleStrategy {
	private int price;
	private String buyer;

	public BuyoutStrategy(int price)
	{
		this.price = price;
	}

	@Override
	public void offer(Item item, int amount, String username) {
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
	public int getOfferAmount() {
		return price;
	}

	@Override
	public SaleStrategyType strategyType() {
		return SaleStrategyType.BUYOUT;
	}
}
