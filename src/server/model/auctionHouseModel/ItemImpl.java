package server.model.auctionHouseModel;

import server.model.UpdateBroadcasterImpl;
import shared.network.model.Item;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ItemImpl extends UnicastRemoteObject implements Item {
	private final String itemID;

	public ItemImpl(String itemID) throws RemoteException {
		this.itemID = itemID;
	}

	@Override
	public String getItemID() throws RemoteException {
		return itemID;
	}

	@Override
	public void userSaleStrategy(int amount, String username) throws RemoteException {
		try {
			UpdateBroadcasterImpl.getInstance(itemID).broadcast();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		System.out.println("STRATEGY TRIGGERED");
	}
}
