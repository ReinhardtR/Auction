package server.model.auctionHouseModel;

import server.model.UpdateBroadcasterImpl;
import shared.network.model.Item;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ItemImpl extends UnicastRemoteObject implements Item {
	private final String itemID;
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
	public int getOfferAmount() throws RemoteException {
		return strategy.getOfferAmount();
	}

	@Override
	public void userSaleStrategy(int amount, String username) throws RemoteException {
		try {
			strategy.offer(amount, username);
			UpdateBroadcasterImpl.getInstance(itemID).broadcast();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		System.out.println("STRATEGY TRIGGERED");
	}
}
