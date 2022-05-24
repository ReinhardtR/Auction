package client.network;

import client.model.ItemCacheProxy;
import client.model.ItemCacheProxyImpl;
import shared.SaleStrategyType;
import shared.network.client.SharedClient;
import shared.network.server.MainServer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ClientImpl extends UnicastRemoteObject implements SharedClient, LocalClient {
	private final PropertyChangeSupport support;
	private final MainServer server;

	public ClientImpl() throws RemoteException, NotBoundException {
		support = new PropertyChangeSupport(this);

		Registry registry = LocateRegistry.getRegistry("localhost", 1099);
		server = (MainServer) registry.lookup("Server");
		server.getCustomerServer().getBroadcaster().registerClient(this);

		// Unregister as listener if program shuts down.
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			try {
				unregisterClient();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}));
	}

	@Override
	public void onServerEvent(String eventName, String itemID, Serializable newValue) throws RemoteException {
		System.out.println(eventName + " " + itemID);
		support.firePropertyChange(eventName, itemID, newValue);
	}

	// Not used?
	@Override
	public ItemCacheProxy getItem(String itemID) throws RemoteException {
		return new ItemCacheProxyImpl(server.getCustomerServer().getItem(itemID));
	}

	@Override
	public List<ItemCacheProxy> getAllItems() throws RemoteException {
		List<ItemCacheProxy> items = new ArrayList<>();

		server.getCustomerServer().getAllItemsInCart().forEach((item) -> {
			try {
				items.add(new ItemCacheProxyImpl(item));
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});

		return items;
	}

	@Override
	public void unregisterClient() throws RemoteException {
		server.getCustomerServer().getBroadcaster().unregisterClient(this);
	}

	@Override
	public void createItem(String title, String description, String tags, SaleStrategyType saleType, String username, double offer, String endtime) throws RemoteException {
		server.getSalesmanServer().createItem(title, description, tags, saleType, username, offer, endtime);
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
