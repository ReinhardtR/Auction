package server.model.item;

import server.model.item.SaleStrategy.SaleStrategy;
import shared.network.model.Item;

import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ItemImpl extends UnicastRemoteObject implements Item {
	private final String itemID;
	private final SaleStrategy strategy;
	private final PropertyChangeSupport support;

	public ItemImpl(String itemID, SaleStrategy strategy) throws RemoteException {
		support = new PropertyChangeSupport(this);
		this.itemID = itemID;
		this.strategy = strategy;
	}

	@Override
	public String getItemID() throws RemoteException {
		return itemID;
	}

	@Override
	public int getOfferAmount() throws RemoteException {
		return strategy.getOfferAmount();
	}

	@Override
	public void userSaleStrategy(String itemID, int amount, String username) throws RemoteException {
		System.out.println("KLAR TIL AT OPDATERER OG BROADCAST");
		strategy.offer(amount, username);
	}
}
