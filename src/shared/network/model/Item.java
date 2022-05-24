package shared.network.model;

import shared.SaleStrategyType;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.temporal.Temporal;

public interface Item extends Remote {

	String getItemID() throws RemoteException;

	String getSellerUsername() throws RemoteException;

	String getTitle() throws RemoteException;

	String getDescription() throws RemoteException;

	String getTags() throws RemoteException;

	Temporal getEndTimestamp() throws RemoteException;

	void userSaleStrategy(double amount, String username) throws RemoteException;

	double getOfferAmount() throws RemoteException;

	boolean getIsSold() throws RemoteException;

	String getBuyerUsername() throws RemoteException;

	SaleStrategyType getStrategyType() throws RemoteException;
}
