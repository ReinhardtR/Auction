package server.model;

import shared.network.client.SharedClient;
import shared.network.model.UpdateBroadcaster;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class UpdateBroadcasterImpl extends UnicastRemoteObject implements UpdateBroadcaster {
	private final String itemID;
	private final List<SharedClient> listeners;

	public UpdateBroadcasterImpl(String itemID) throws RemoteException {
		this.itemID = itemID;
		listeners = new ArrayList<>();
	}

	@Override
	protected void broadcast() {
		listeners.forEach((listener) -> {
			try {
				listener.onNewBid(itemID);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public void addListener(SharedClient client) throws RemoteException {
		listeners.add(client);
	}

	@Override
	public void removeListener(SharedClient client) throws RemoteException {
		listeners.remove(client);
	}
}
