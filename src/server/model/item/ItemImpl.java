package server.model.item;

import server.model.broadcaster.UpdateBroadcaster;
import server.model.broadcaster.UpdateBroadcasterImpl;
import server.model.item.SaleStrategy.SaleStrategy;
import shared.SaleStrategyType;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.temporal.Temporal;

public class ItemImpl extends UnicastRemoteObject implements Item {
	private final UpdateBroadcaster broadcaster;
	private final String itemID;

	//Enum
	private final SaleStrategy strategy;

	public ItemImpl(String itemID, SaleStrategy strategy) throws RemoteException {
		this.itemID = itemID;
		this.strategy = strategy;
		broadcaster = new UpdateBroadcasterImpl();
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

	@Override
	public String getBuyerUsername() throws RemoteException {
		return null;
	}

	@Override
	public UpdateBroadcaster getUpdateBroadcaster() throws RemoteException {
		return broadcaster;
	}

	@Override
	public SaleStrategyType getStrategyType() throws RemoteException {
		return strategy.strategyType();
	}

	@Override
	public void userSaleStrategy(double amount, String username) throws RemoteException {
		strategy.offer(this, amount, username);
		Cart.getInstance().updateItemOffer(this);
	}

	@Override
	public String toString() {
		return "ItemImpl{" +
						"broadcaster=" + broadcaster +
						", itemID='" + itemID + '\'' +
						", strategy=" + strategy +
						'}';
	}
}
