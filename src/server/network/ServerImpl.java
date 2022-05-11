package server.network;


import server.model.auctionHouseModel.Cart;
import server.model.auctionHouseModel.SaleStrategy.AuctionStrategy;
import server.model.auctionHouseModel.ItemImpl;
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
