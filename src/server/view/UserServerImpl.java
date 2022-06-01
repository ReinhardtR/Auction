package server.view;

import shared.network.server.CustomerServer;
import shared.network.server.UserServer;
import shared.network.server.SalesmanServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UserServerImpl extends UnicastRemoteObject implements UserServer {

	private final CustomerServer customerServer;
	private final SalesmanServer salesmanServer;


	public UserServerImpl(CustomerServerImpl customerServer, SalesmanServerImpl salesmanServer) throws RemoteException {
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
