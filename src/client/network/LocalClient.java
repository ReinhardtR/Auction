package client.network;

import server.model.auctionHouseModel.ItemImpl;
import shared.network.model.Item;
import shared.utils.PropertyChangeSubject;

import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;

public interface LocalClient extends PropertyChangeSubject {
	Item getItem(String itemID) throws RemoteException;
}
