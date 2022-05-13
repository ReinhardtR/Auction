package server.model.item;

import server.model.broadcaster.UpdateBroadcaster;
import shared.SaleStrategyType;
import shared.network.model.GenerelItems;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Item extends GenerelItems {
	UpdateBroadcaster getUpdateBroadcaster() throws RemoteException;

	SaleStrategyType strategyType() throws RemoteException;
}
