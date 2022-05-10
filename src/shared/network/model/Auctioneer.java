package shared.network.model;


import shared.network.client.SharedClient;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Auctioneer extends Remote {
	void addListener(SharedClient client) throws RemoteException;

	void removeListener(SharedClient client) throws RemoteException;
}
