package server.view;

import server.service.salesman.SalesmanItemService;
import server.service.salesman.SalesmanItemServiceImpl;
import shared.SaleStrategyType;
import shared.network.server.SalesmanServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SalesmanServerImpl extends UnicastRemoteObject implements SalesmanServer {

	private final SalesmanItemService salesmanItemService;

	public SalesmanServerImpl() throws RemoteException {
		salesmanItemService = new SalesmanItemServiceImpl();
	}

	@Override
	public void createItem(String title, String description, String tags, SaleStrategyType saleType, String username, double offer, String endtime) throws RemoteException {
		salesmanItemService.makeItem(title, description, tags, saleType, username, offer, endtime);
	}
}
