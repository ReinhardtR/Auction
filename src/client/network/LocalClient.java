package client.network;

import client.model.ItemCacheProxy;
import shared.utils.PropertyChangeSubject;

import java.rmi.RemoteException;
import java.util.List;

public interface LocalClient extends PropertyChangeSubject {
	ItemCacheProxy getItem(String itemID) throws RemoteException;

	List<ItemCacheProxy> getAllItems() throws RemoteException;

	void unregisterClient() throws RemoteException;

	void createItem() throws RemoteException;
}
