package shared.network.client;

import client.model.ObservableItem;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SharedClient extends Remote {
	void onNewBid(String itemID) throws RemoteException;
}
