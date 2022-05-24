package server.view;


import server.model.broadcaster.UpdateBroadcaster;
import server.model.broadcaster.UpdateBroadcasterImpl;
import server.model.item.Cart;
import shared.network.model.Item;
import shared.network.server.CustomerServer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class CustomerServerImpl extends UnicastRemoteObject implements CustomerServer, PropertyChangeListener {
	private final UpdateBroadcasterImpl broadcaster;

	public CustomerServerImpl() throws RemoteException {
		broadcaster = new UpdateBroadcasterImpl();
		Cart.getInstance().addListenerToAllEvents(this);
		Cart.getInstance().getManyItems();
	}

	@Override
	public Item getItem(String itemID) throws RemoteException {
		return Cart.getInstance().getItem(itemID);
	}

	@Override
	public List<Item> getAllItemsInCart() throws RemoteException {
		return Cart.getInstance().returnAllItemsInCart();
	}

	@Override
	public UpdateBroadcaster getBroadcaster() {
		return broadcaster;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		try {
			broadcaster.broadcastEventForItem(event.getPropertyName(), (String) event.getNewValue());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
