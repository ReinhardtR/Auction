package shared.network.model;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Item extends Remote {
	String getItemID() throws RemoteException;

	void userSaleStrategy(int amount, String username) throws RemoteException;

	int getOfferAmount() throws RemoteException;
}