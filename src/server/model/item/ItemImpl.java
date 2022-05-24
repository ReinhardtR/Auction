package server.model.item;

import server.model.item.SaleStrategy.SaleStrategy;
import shared.SaleStrategyType;
import shared.network.model.Item;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.temporal.Temporal;

public class ItemImpl extends UnicastRemoteObject implements Item {
	private final String sellerUsername;
	private final String title;
	private final String description;
	private final String tags;
	//Enum
	private final SaleStrategy strategy;
	private String itemID;

	public ItemImpl(String itemID, String sellerUsername, String title, String description, String tags, SaleStrategy strategy) throws RemoteException {
		this.itemID = itemID;
		this.sellerUsername = sellerUsername;
		this.title = title;
		this.description = description;
		this.tags = tags;
		this.strategy = strategy;
	}

	public ItemImpl(String sellerUsername, String title, String description, String tags, SaleStrategy strategy) throws RemoteException {
		this.sellerUsername = sellerUsername;
		this.title = title;
		this.description = description;
		this.tags = tags;
		this.strategy = strategy;
	}

	@Override
	public String getItemID() throws RemoteException {
		return itemID;
	}

	@Override
	public String getSellerUsername() throws RemoteException {
		return sellerUsername;
	}

	@Override
	public String getTitle() throws RemoteException {
		return title;
	}

	@Override
	public String getDescription() throws RemoteException {
		return description;
	}

	@Override
	public String getTags() throws RemoteException {
		return tags;
	}

	@Override
	public Temporal getEndTimestamp() throws RemoteException {
		return strategy.getEndTime();
	}

	@Override
	public double getOfferAmount() throws RemoteException {
		return strategy.getOfferAmount();
	}

	// Synchronized?
	@Override
	public boolean getIsSold() throws RemoteException {
		return strategy.getIsSold();
	}

	@Override
	public String getBuyerUsername() throws RemoteException {
		return null;
	}

	@Override
	public SaleStrategyType getStrategyType() throws RemoteException {
		return strategy.strategyType();
	}

	@Override
	public void userSaleStrategy(double amount, String username) throws RemoteException {
		strategy.offer(this, amount, username);
	}

	@Override
	public String toString() {
		return "ItemImpl{" +
						", itemID='" + itemID + '\'' +
						", strategy=" + strategy +
						'}';
	}
}
