package server.view;

import shared.network.server.SalesmanServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SalesmanServerImpl extends UnicastRemoteObject implements SalesmanServer {


	public SalesmanServerImpl() throws RemoteException {
	}
}
