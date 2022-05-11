package server.model.item;

import server.model.broadcaster.UpdateBroadcaster;
import server.model.broadcaster.UpdateBroadcasterImpl;
import server.model.item.SaleStrategy.SaleStrategy;
import shared.network.model.Item;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.temporal.Temporal;

public class ItemImpl extends UnicastRemoteObject implements Item {
	private final UpdateBroadcaster broadcaster;
	private final String itemID;
	private final SaleStrategy strategy;
	private final Temporal endTimestamp;

	public ItemImpl(String itemID, Temporal endTimestamp, SaleStrategy strategy) throws RemoteException {
		broadcaster = new UpdateBroadcasterImpl(itemID);

		this.itemID = itemID;
		this.endTimestamp = endTimestamp;
		this.strategy = strategy;
	}

	@Override
	public String getItemID() throws RemoteException {
		return itemID;
	}

	@Override
	public Temporal getEndTimestamp() throws RemoteException {
		return endTimestamp;
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
