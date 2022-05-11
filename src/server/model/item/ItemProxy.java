package server.model.item;

import server.model.broadcaster.UpdateBroadcaster;
import shared.network.model.Item;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

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
	public void userSaleStrategy(int amount, String username) throws RemoteException {
		System.out.println("ITEM PROXY: " + amount);
		item.userSaleStrategy(amount, username);
	}

	@Override
	public int getOfferAmount() throws RemoteException {
		return item.getOfferAmount();
	}

	@Override
	public UpdateBroadcaster getUpdateBroadcaster() throws RemoteException {
		return item.getUpdateBroadcaster();
	}
}
