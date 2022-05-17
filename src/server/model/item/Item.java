package server.model.item;

import server.model.broadcaster.UpdateBroadcaster;
import shared.network.model.GenerelItems;

import java.rmi.RemoteException;

public interface Item extends GenerelItems {
	UpdateBroadcaster getUpdateBroadcaster() throws RemoteException;
}
