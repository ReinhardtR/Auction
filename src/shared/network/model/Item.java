package shared.network.model;

import server.model.broadcaster.UpdateBroadcaster;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Item extends Remote {
	String getItemID() throws RemoteException;

	void userSaleStrategy(int amount, String username) throws RemoteException;

	int getOfferAmount() throws RemoteException;

	UpdateBroadcaster getUpdateBroadcaster() throws RemoteException;
}
