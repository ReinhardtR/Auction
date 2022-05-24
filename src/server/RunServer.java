package server;


import server.view.CustomerServerImpl;
import server.view.MainServerImpl;
import server.view.SalesmanServerImpl;
import shared.network.server.MainServer;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RunServer {
	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		MainServer mainServer = new MainServerImpl(new CustomerServerImpl(), new SalesmanServerImpl());


		Registry registry = LocateRegistry.createRegistry(1099);
		registry.bind("Server", mainServer);

		System.out.println("Server is up!");
	}
}
