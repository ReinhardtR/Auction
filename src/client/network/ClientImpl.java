package client.network;

import server.model.item.Item;
import shared.network.client.SharedClient;
import shared.network.server.MainServer;

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
	private final MainServer server;

	public ClientImpl() throws RemoteException, NotBoundException {
		support = new PropertyChangeSupport(this);

		Registry registry = LocateRegistry.getRegistry("localhost", 1099);
		server = (MainServer) registry.lookup("Server");
		server.getCustomerServer().getBroadcaster().registerClient(this);
	}

	// Some listen to items, some listen to everything
	// Maybe make two different support instances and create a listenToItem method
	@Override
	public void onServerEvent(String eventName, String itemID) throws RemoteException {
		if (itemID != null) {
			support.firePropertyChange(eventName + itemID, null, itemID);
		}

		support.firePropertyChange(eventName, null, itemID);
	}

	// Not used
	@Override
	public Item getItem(String itemID) throws RemoteException {
		return server.getCustomerServer().getItem(itemID);
	}

	@Override
	public List<Item> getAllItems() throws RemoteException {
		return server.getCustomerServer().getAllItemsInCart();
	}

	@Override
	public void unregisterClient() throws RemoteException {
		server.getCustomerServer().getBroadcaster().unregisterClient(this);
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
