package client.model.item;

import shared.SaleStrategyType;
import shared.network.model.Item;

import java.rmi.RemoteException;
import java.time.temporal.Temporal;

public class ItemCacheProxyImpl implements ItemCacheProxy {
	private final Item item;
	private final String itemID;
	private final String salesmanUsername;
	private final String title;
	private final String description;
	private final String tags;
	private final Temporal endTimestamp;
	private final SaleStrategyType strategyType;

	public ItemCacheProxyImpl(Item item) throws RemoteException {
		this.item = item;

		// Cache
		itemID = item.getItemID();
		salesmanUsername = item.getSalesmanUsername();
		title = item.getTitle();
		description = item.getDescription();
		tags = item.getTags();
		strategyType = item.getStrategyType();
		endTimestamp = item.getEndTimestamp();
	}

	@Override
	public String getItemID() {
		return itemID;
	}

	@Override
	public String getSalesmanUsername() {
		return salesmanUsername;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getTags() {
		return tags;
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
