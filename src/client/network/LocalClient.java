package client.network;

import client.model.item.ItemCacheProxy;
import shared.SaleStrategyType;
import shared.utils.PropertyChangeSubject;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface LocalClient extends PropertyChangeSubject {

	List<ItemCacheProxy> getAllItems() throws RemoteException;

	void unregisterClient() throws RemoteException;

	void createItem(String title, String description, String tags, SaleStrategyType saleType, String username, double offer, LocalTime endtime, LocalDate endDate) throws RemoteException;
}
