package shared.network.model;

import shared.SaleStrategyType;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.temporal.Temporal;

public interface  GenerelItems extends Remote {

	String getItemID() throws RemoteException;

	Temporal getEndTimestamp() throws RemoteException;

	void userSaleStrategy(double amount, String username) throws RemoteException;

	double getOfferAmount() throws RemoteException;

	String getBuyerUsername() throws RemoteException;

	void setAsSold() throws RemoteException;

	SaleStrategyType getStrategyType() throws RemoteException;

}
