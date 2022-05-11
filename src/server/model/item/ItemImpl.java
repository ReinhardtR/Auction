package server.model.item;

import server.model.broadcaster.UpdateBroadcaster;
import server.model.broadcaster.UpdateBroadcasterImpl;
import server.model.item.SaleStrategy.SaleStrategy;
import shared.network.model.Item;

import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ItemImpl extends UnicastRemoteObject implements Item {
	private final PropertyChangeSupport support;
	private final UpdateBroadcaster broadcaster;
	private final String itemID;
	private final SaleStrategy strategy;

	public ItemImpl(String itemID, SaleStrategy strategy) throws RemoteException {
		support = new PropertyChangeSupport(this);
		broadcaster = new UpdateBroadcasterImpl(itemID);

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
	public UpdateBroadcaster getUpdateBroadcaster() throws RemoteException {
		return broadcaster;
	}

	@Override
	public void userSaleStrategy(int amount, String username) throws RemoteException {
		strategy.offer(amount, username);
		broadcaster.broadcast();
	}
}
