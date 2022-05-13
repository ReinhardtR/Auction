package server.model.broadcaster;

import shared.network.client.SharedClient;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UpdateBroadcaster extends Remote {
	void broadcastEvent(String eventName) throws RemoteException;
	void broadcastEventForItem(String eventName, String itemID) throws RemoteException;

	void registerClient(SharedClient client) throws RemoteException;

	void unregisterClient(SharedClient client) throws RemoteException;
}
