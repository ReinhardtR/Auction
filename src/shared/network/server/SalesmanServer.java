package shared.network.server;

import shared.SaleStrategyType;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SalesmanServer extends Remote {


	void createItem(String title, String description, String tags, SaleStrategyType saleType, String username, double offer, String endtime) throws RemoteException;
}
