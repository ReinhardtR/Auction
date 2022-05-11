package server.model.broadcaster;

import shared.network.client.SharedClient;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class UpdateBroadcasterImpl extends UnicastRemoteObject implements UpdateBroadcaster {
	private final List<SharedClient> listeners;
	private final String itemID;

	public UpdateBroadcasterImpl(String itemID) throws RemoteException {
		listeners = new ArrayList<>();
		this.itemID = itemID;
	}

	public void broadcast() {
		listeners.forEach((listener) -> {
			try {
				System.out.println("AM BROADCASTING");
				listener.onNewBid(itemID);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public void registerClient(SharedClient client) throws RemoteException {
		listeners.add(client);
	}

	@Override
	public void unregisterClient(SharedClient client) throws RemoteException {
		listeners.remove(client);
	}
}
