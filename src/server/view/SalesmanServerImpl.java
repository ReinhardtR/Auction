package server.view;

import server.service.salesman.SalesmanItemService;
import server.service.salesman.SalesmanItemServiceImpl;
import shared.SaleStrategyType;
import shared.network.server.SalesmanServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class SalesmanServerImpl extends UnicastRemoteObject implements SalesmanServer {

	private final SalesmanItemService salesmanItemService;

	public SalesmanServerImpl() throws RemoteException {
		salesmanItemService = new SalesmanItemServiceImpl();
	}

	@Override
	public void createItem(String title, String description, String tags, SaleStrategyType saleType, String username, double offer, LocalTime endtime, LocalDate endDate) throws RemoteException {
		salesmanItemService.createAndSendItemToDB(title, description, tags, saleType, username, offer, endtime,endDate);
	}
}
