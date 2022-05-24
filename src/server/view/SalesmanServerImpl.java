package server.view;

import server.MakeItemForSale;
import server.MakeItemForSaleImpl;
import shared.SaleStrategyType;
import shared.network.server.SalesmanServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SalesmanServerImpl extends UnicastRemoteObject implements SalesmanServer {

	private final MakeItemForSale makeItemForSale;

	public SalesmanServerImpl() throws RemoteException {
		makeItemForSale = new MakeItemForSaleImpl();
	}

	@Override
	public void createItem(String title, String description, String tags, SaleStrategyType saleType, String username, double offer, String endtime) throws RemoteException {
		makeItemForSale.makeItem(title, description, tags, saleType, username, offer, endtime);
	}
}
