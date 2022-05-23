package server.model.item;

import server.model.item.SaleStrategy.SaleStrategy;
import shared.SaleStrategyType;
import shared.network.model.Item;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.temporal.Temporal;

public class ItemImpl extends UnicastRemoteObject implements Item {
	private final String itemID;

	//Enum
	private final SaleStrategy strategy;

	public ItemImpl(String itemID, SaleStrategy strategy) throws RemoteException {
		this.itemID = itemID;
		this.strategy = strategy;
	}

	@Override
	public String getItemID() throws RemoteException {
		return itemID;
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
