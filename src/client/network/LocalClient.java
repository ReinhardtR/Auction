package client.network;

import shared.network.model.Item;
import shared.utils.PropertyChangeSubject;

import java.rmi.RemoteException;

public interface LocalClient extends PropertyChangeSubject {
	Item getItem(String itemID) throws RemoteException;
}
