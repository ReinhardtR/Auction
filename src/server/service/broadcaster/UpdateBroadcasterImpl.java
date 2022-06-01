package server.service.broadcaster;

import shared.network.client.SharedClient;

import java.io.Serializable;
import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class UpdateBroadcasterImpl extends UnicastRemoteObject implements UpdateBroadcaster {
	private final List<SharedClient> listeners;

	public UpdateBroadcasterImpl() throws RemoteException {
		listeners = new ArrayList<>();
	}

	// Loop through listeners and call the RMI Callback function exposed by the client.
	@Override
	public void broadcastEventForItem(String eventName, String itemID, Serializable newValue) throws RemoteException {
		listeners.forEach((listener) -> {
			try {
				listener.onServerEvent(eventName, itemID, newValue);
			} catch (ConnectException e) {
				listeners.remove(listener);
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
