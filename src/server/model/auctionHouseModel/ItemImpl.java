package server.model.auctionHouseModel;

import server.model.UpdateBroadcasterImpl;
import shared.network.model.Item;

import java.rmi.RemoteException;

public class ItemImpl implements Item {
	private final String itemID;

	public ItemImpl(String itemID) {
		this.itemID = itemID;
	}

	@Override
	public String getItemID() {
		return itemID;
	}

	@Override
	public void userSaleStrategy(int amount, String username) {
		try {
			UpdateBroadcasterImpl.getInstance(itemID).broadcast();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		System.out.println("STRATEGY TRIGGERED");
	}
}
