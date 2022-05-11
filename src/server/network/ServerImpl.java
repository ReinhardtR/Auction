package server.network;


import server.model.item.Cart;
import server.model.item.ItemImpl;
import server.model.item.SaleStrategy.AuctionStrategy;
import shared.network.model.Item;
import shared.network.server.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerImpl extends UnicastRemoteObject implements Server {

	public ServerImpl() throws RemoteException {
		//Til random item
		Cart.getInstance().setItem(new ItemImpl("123", new AuctionStrategy()));
	}

	@Override
	public Item getItem(String itemID) throws RemoteException {
		return Cart.getInstance().getItem(1);
	}
}
