package client.network;

import client.model.item.ItemCacheProxy;
import client.model.item.ItemCacheProxyImpl;
import shared.SaleStrategyType;
import shared.network.client.SharedClient;
import shared.network.server.UserServer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ClientImpl extends UnicastRemoteObject implements SharedClient, LocalClient {
	private final PropertyChangeSupport support;
	private final UserServer server;

	public ClientImpl() throws RemoteException, NotBoundException {
		support = new PropertyChangeSupport(this);

		Registry registry = LocateRegistry.getRegistry("localhost", 1099);
		server = (UserServer) registry.lookup("Server");
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
		support.firePropertyChange(eventName, itemID, newValue);
	}


	// Get all Items from the server and returns a list of Items with cache.
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
	public void createItem(String title, String description, String tags, SaleStrategyType saleType, String username, double offer, LocalTime endtime, LocalDate endDate) throws RemoteException {
		server.getSalesmanServer().createItem(title, description, tags, saleType, username, offer, endtime, endDate);
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
