package server.network;


import server.model.broadcaster.UpdateBroadcaster;
import server.model.broadcaster.UpdateBroadcasterImpl;
import server.model.item.Cart;
import server.model.item.ItemImpl;
import server.model.item.ItemProxy;
import server.model.item.SaleStrategy.AuctionStrategy;
import server.model.item.SaleStrategy.BuyoutStrategy;
import server.model.item.Item;
import shared.network.server.Server;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.List;

public class ServerImpl extends UnicastRemoteObject implements Server, PropertyChangeListener {
	private final UpdateBroadcasterImpl broadcaster;

	public ServerImpl() throws RemoteException {
		broadcaster = new UpdateBroadcasterImpl();
		Cart.getInstance().addListenerToAllEvents(this);

		//Til random item
		Temporal endDateTime = LocalDateTime.of(2022, 5, 16, 11, 20);
		Cart.getInstance().addItem(new ItemImpl("123", endDateTime, new AuctionStrategy()));
		Cart.getInstance().addItem(new ItemImpl("456", endDateTime, new BuyoutStrategy(69)));
	}

	@Override
	public Item getItem(String itemID) throws RemoteException {
		return new ItemProxy(Cart.getInstance().getItem(itemID));
	}

	@Override
	public List<Item> getAllItemsInCart() throws RemoteException {
		return Cart.getInstance().returnAllItemsInCart();
	}

	@Override
	public UpdateBroadcaster getBroadcaster() throws RemoteException {
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
