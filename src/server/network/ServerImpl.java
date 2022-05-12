package server.network;


import server.model.item.Cart;
import server.model.item.ItemImpl;
import server.model.item.ItemProxy;
import server.model.item.SaleStrategy.AuctionStrategy;
import server.model.item.SaleStrategy.BuyoutStrategy;
import shared.network.model.Item;
import shared.network.server.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.List;

public class ServerImpl extends UnicastRemoteObject implements Server {

	public ServerImpl() throws RemoteException {
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
}
