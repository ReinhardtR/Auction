package server.model.item;

import server.model.broadcaster.UpdateBroadcaster;
import shared.network.model.Item;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.temporal.Temporal;

// TODO: doesn't really do nothin'
public class ItemProxy extends UnicastRemoteObject implements Item {
	private final Item item;

	public ItemProxy(Item item) throws RemoteException {
		this.item = item;
	}

	@Override
	public String getItemID() throws RemoteException {
		return item.getItemID();
	}

	@Override
	public Temporal getEndTimestamp() throws RemoteException {
		return item.getEndTimestamp();
	}

	@Override
	public void userSaleStrategy(int amount, String username) throws RemoteException {
		System.out.println("ITEM PROXY: " + amount);
		item.userSaleStrategy(amount, username);
	}

	@Override
	public int getOfferAmount() throws RemoteException {
		return item.getOfferAmount();
	}

	@Override
	public void setAsSold() throws RemoteException {
		item.setAsSold();
	}

	@Override
	public UpdateBroadcaster getUpdateBroadcaster() throws RemoteException {
		return item.getUpdateBroadcaster();
	}

	@Override
	public String strategyType() throws RemoteException {
		return item.strategyType();
	}
}
