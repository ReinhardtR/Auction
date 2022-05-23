package client.network;

import server.model.item.Item;
import shared.network.client.SharedClient;
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
		// Event is not item-specific.
		if (itemID == null) {
			support.firePropertyChange(eventName, null, null);
		}

		support.firePropertyChange(eventName + itemID, null, itemID);
	}

	// Not used
	@Override
	public Item getItem(String itemID) throws RemoteException {
		return server.getItem(itemID);
	}

	@Override
	public List<Item> getAllItems() throws RemoteException {
		return server.getAllItemsInCart();
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
