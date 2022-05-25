package shared.network.server;

import server.service.broadcaster.UpdateBroadcaster;
import shared.network.model.Item;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CustomerServer extends Remote {
	Item getItem(String itemID) throws RemoteException;

	List<Item> getAllItemsInCart() throws RemoteException;

	UpdateBroadcaster getBroadcaster() throws RemoteException;
}
