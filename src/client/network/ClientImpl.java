package client.network;

import shared.network.client.SharedClient;
import shared.network.model.Item;
import shared.network.server.Server;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ClientImpl extends UnicastRemoteObject implements SharedClient, LocalClient {
	private final PropertyChangeSupport support;
	private final Server server;

	public ClientImpl() throws RemoteException, NotBoundException {
		support = new PropertyChangeSupport(this);

		Registry registry = LocateRegistry.getRegistry("localhost", 1099);
		server = (Server) registry.lookup("Server");
		server.getBroadcaster().registerClient(this);
	}

	@Override
	public void onServerEvent(String eventName, String itemID) throws RemoteException {
		support.firePropertyChange(eventName, null, itemID);
		System.out.println(eventName + " " + itemID);
	}

	// Not used?
	@Override
	public Item getItem(String itemID) throws RemoteException {
		return server.getItem(itemID);
	}

	@Override
	public List<Item> getAllItems() throws RemoteException {
		return server.getAllItemsInCart();
	}

	@Override
	public void unregisterClient() throws RemoteException {
		server.getBroadcaster().unregisterClient(this);
	}

	@Override
	public void addListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}

	@Override
	public void removeListener(PropertyChangeListener listener) {
		support.removePropertyChangeListener(listener);
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
