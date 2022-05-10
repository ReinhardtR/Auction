package server.model.auctionHouseModel;

import shared.network.client.SharedClient;
import shared.network.model.Auctioneer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class AuctioneerImpl extends UnicastRemoteObject implements Auctioneer {
	private final String itemID;
	private final List<SharedClient> listeners;

	public AuctioneerImpl(String itemID) throws RemoteException {
		this.itemID = itemID;
		listeners = new ArrayList<>();
	}

	protected void announceNewBid() {
		listeners.forEach((listener) -> {
			try {
				listener.onNewBid(itemID);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});
	}

	public void addListener(SharedClient client) throws RemoteException {
		listeners.add(client);
	}

	public void removeListener(SharedClient client) throws RemoteException {
		listeners.remove(client);
	}
}
