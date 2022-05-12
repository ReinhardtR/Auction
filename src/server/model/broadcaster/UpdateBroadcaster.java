package server.model.broadcaster;

import shared.network.client.SharedClient;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UpdateBroadcaster extends Remote {
	void broadcast(String eventName) throws RemoteException;

	void registerClient(SharedClient client) throws RemoteException;

	void unregisterClient(SharedClient client) throws RemoteException;
}
