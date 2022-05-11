package server.network;


import server.model.auctionHouseModel.AuctionStrategy;
import server.model.auctionHouseModel.ItemImpl;
import shared.network.model.Item;
import shared.network.server.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerImpl extends UnicastRemoteObject implements Server {
	private final Item item;

	public ServerImpl() throws RemoteException {
		this.item = new ItemImpl("123", new AuctionStrategy());
	}

	@Override
	public Item getItem(String itemID) throws RemoteException {
		return item;
	}
}