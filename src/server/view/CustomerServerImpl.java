package server.view;


import server.service.broadcaster.UpdateBroadcaster;
import server.service.broadcaster.UpdateBroadcasterImpl;
import server.service.customer.CustomerItemService;
import shared.network.model.Item;
import shared.network.server.CustomerServer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class CustomerServerImpl extends UnicastRemoteObject implements CustomerServer, PropertyChangeListener {
	private final UpdateBroadcasterImpl broadcaster;

	public CustomerServerImpl() throws RemoteException {
		broadcaster = new UpdateBroadcasterImpl();

		//Siden søgning efter items ikke er implementeret får brugeren bare de første 10 items fra serveren med denne metode
		CustomerItemService.getInstance().addListener(this);
		CustomerItemService.getInstance().getManyItems();
	}

	@Override
	public Item getItem(String itemID) throws RemoteException {
		return CustomerItemService.getInstance().getItem(itemID);
	}

	@Override
	public List<Item> getAllItemsInCart() throws RemoteException {
		return CustomerItemService.getInstance().getAllStoredItems();
	}

	@Override
	public UpdateBroadcaster getBroadcaster() {
		return broadcaster;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		try {
			System.out.println("EVENT FROM: " + event.getSource().getClass().getName());
			broadcaster.broadcastEventForItem(event.getPropertyName(), (String) event.getOldValue(), (Serializable) event.getNewValue());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
