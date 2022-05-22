package client.network;

import server.model.item.Item;
import shared.network.server.Server;
import shared.utils.PropertyChangeSubject;

import java.rmi.RemoteException;
import java.util.List;

public interface LocalClient extends PropertyChangeSubject {
	Item getItem(String itemID) throws RemoteException;

	List<Item> getAllItems() throws RemoteException;

	void unregisterClient() throws RemoteException;
}
