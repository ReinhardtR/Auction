package shared.network.model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.temporal.Temporal;

public interface GenerelItems extends Remote {

	String getItemID() throws RemoteException;

	Temporal getEndTimestamp() throws RemoteException;

	void userSaleStrategy(int amount, String username) throws RemoteException;

	int getOfferAmount() throws RemoteException;

	void setAsSold() throws RemoteException;

}
