package shared.network.server;

import shared.network.model.Item;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
	Item getItem(String itemID) throws RemoteException;
}
