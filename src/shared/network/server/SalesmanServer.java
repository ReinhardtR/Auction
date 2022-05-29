package shared.network.server;

import shared.SaleStrategyType;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalTime;

public interface SalesmanServer extends Remote {


	void createItem(String title, String description, String tags, SaleStrategyType saleType, String username, double offer, LocalTime endtime, LocalDate endDate) throws RemoteException;
}
