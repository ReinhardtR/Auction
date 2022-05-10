package shared.network.model;

import shared.network.client.SharedClient;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UpdateBroadcaster extends Remote {
	void broadcast() throws RemoteException;

	void addListener(SharedClient client) throws RemoteException;

	void removeListener(SharedClient client) throws RemoteException;
}
