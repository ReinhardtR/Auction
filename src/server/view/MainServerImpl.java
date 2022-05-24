package server.view;

import shared.network.server.CustomerServer;
import shared.network.server.MainServer;
import shared.network.server.SalesmanServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MainServerImpl extends UnicastRemoteObject implements MainServer {

	private final CustomerServer customerServer;
	private final SalesmanServer salesmanServer;


	public MainServerImpl(CustomerServerImpl customerServer, SalesmanServerImpl salesmanServer) throws RemoteException {
		this.customerServer = customerServer;
		this.salesmanServer = salesmanServer;
	}

	@Override
	public CustomerServer getCustomerServer() {
		return customerServer;
	}

	@Override
	public SalesmanServer getSalesmanServer() {
		return salesmanServer;
	}
}
