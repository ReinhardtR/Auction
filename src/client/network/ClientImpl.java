package client.network;

import server.model.auctionHouseModel.ItemImpl;
import server.model.auctionHouseModel.broadcaster.UpdateBroadcaster;
import server.model.auctionHouseModel.broadcaster.UpdateBroadcasterImpl;
import shared.network.client.SharedClient;
import shared.network.model.Item;
import shared.network.server.Server;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl extends UnicastRemoteObject implements SharedClient, LocalClient {
	private final PropertyChangeSupport support;
	private final Server server;

	public ClientImpl() throws RemoteException, NotBoundException {
		support = new PropertyChangeSupport(this);

		Registry registry = LocateRegistry.getRegistry("localhost", 1099);
		server = (Server) registry.lookup("Server");
	}

	@Override
	public void onNewBid(String itemID) throws RemoteException {
		System.out.println("CLIENT FIRE");
		support.firePropertyChange(itemID, null, null);
	}

	@Override
	public Item getItem(String itemID) throws RemoteException {
		UpdateBroadcaster broadcaster = UpdateBroadcasterImpl.getBroadcasterInstance(itemID);
		broadcaster.addListener(this);

		return server.getItem(itemID);
	}

	@Override
	public void addListener(String eventName, PropertyChangeListener listener) {
		support.addPropertyChangeListener(eventName, listener);
	}

	@Override
	public void removeListener(String eventName, PropertyChangeListener listener) {
		support.addPropertyChangeListener(eventName, listener);
	}
}
