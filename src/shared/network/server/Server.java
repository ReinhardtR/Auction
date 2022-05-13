package shared.network.server;

import server.model.broadcaster.UpdateBroadcaster;
import shared.network.client.SharedClient;
import shared.network.model.Item;
import shared.utils.PropertyChangeSubject;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Server extends Remote {
	Item getItem(String itemID) throws RemoteException;

	List<Item> getAllItemsInCart() throws RemoteException;

	UpdateBroadcaster getBroadcaster() throws RemoteException;
}
