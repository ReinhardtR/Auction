package server;


import server.network.ServerImpl;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RunServer {
	public static void main(String[] args) throws RemoteException {
		Registry registry = LocateRegistry.createRegistry(1099);

		ServerImpl server = new ServerImpl();

		try {
			registry.bind("Server", server);
		} catch (AlreadyBoundException e) {
			e.printStackTrace();
		}
		while (true) {

		}
	}
}
