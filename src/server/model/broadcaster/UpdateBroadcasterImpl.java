package server.model.broadcaster;

import shared.network.client.SharedClient;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class UpdateBroadcasterImpl extends UnicastRemoteObject implements UpdateBroadcaster {
	private final List<SharedClient> listeners;

	public UpdateBroadcasterImpl() throws RemoteException {
		listeners = new ArrayList<>();
	}

	@Override
	public void broadcastEvent(String eventName) throws RemoteException {
		listeners.forEach((listener) -> {
			try {
				listener.onServerEvent(eventName, null);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public void broadcastEventForItem(String eventName, String itemID) throws RemoteException {
		listeners.forEach((listener) -> {
			try {
				listener.onServerEvent(eventName, itemID);
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
