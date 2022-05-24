package client.model;

import shared.SaleStrategyType;
import shared.network.model.Item;

import java.rmi.RemoteException;
import java.time.temporal.Temporal;

public class ItemCacheProxyImpl implements ItemCacheProxy {
	private final Item item;

	private final String itemID;
	private final Temporal endTimestamp;
	private final SaleStrategyType strategyType;

	public ItemCacheProxyImpl(Item item) throws RemoteException {
		this.item = item;

		// Cache
		itemID = item.getItemID();
		strategyType = item.getStrategyType();
		endTimestamp = item.getEndTimestamp();
	}

	@Override
	public String getItemID() {
		return itemID;
	}

	@Override
	public Temporal getEndTimestamp() {
		return endTimestamp;
	}

	@Override
	public SaleStrategyType getStrategyType() {
		return strategyType;
	}

	@Override
	public void userSaleStrategy(double amount, String username) throws RemoteException {
		item.userSaleStrategy(amount, username);
	}

	@Override
	public double getOfferAmount() throws RemoteException {
		return item.getOfferAmount();
	}

	@Override
	public boolean getIsSold() throws RemoteException {
		return item.getIsSold();
	}

	@Override
	public String getBuyerUsername() throws RemoteException {
		return item.getBuyerUsername();
	}
}
