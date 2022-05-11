package shared.network.server;

import shared.network.model.Item;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Server extends Remote {
	Item getItem(String itemID) throws RemoteException;

	List<Item> getAllItemsInCart() throws RemoteException;
}
