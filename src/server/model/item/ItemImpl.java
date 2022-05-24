package server.model.item;

import server.model.item.SaleStrategy.SaleStrategy;
import shared.SaleStrategyType;
import shared.network.model.Item;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.temporal.Temporal;

public class ItemImpl extends UnicastRemoteObject implements Item {
	private final String itemID;
	private final String salesmanUsername;
	private final String title;
	private final String description;
	private final String tags;
	private final SaleStrategy strategy;

	public ItemImpl(String itemID, String salesmanUsername, String title, String description, String tags, SaleStrategy strategy) throws RemoteException {
		this.itemID = itemID;
		this.salesmanUsername = salesmanUsername;
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
	public String getSalesmanUsername() throws RemoteException {
		return salesmanUsername;
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
