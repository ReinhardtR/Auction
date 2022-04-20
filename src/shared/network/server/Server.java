package shared.network.server;

import shared.network.client.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {

	AuctionHouse getActionHouse() throws RemoteException;

	void registerAsClient(Client client) throws RemoteException;
}
