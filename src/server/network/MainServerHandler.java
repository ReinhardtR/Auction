package server.network;


import server.model.auctionHouseModel.Item;
import server.model.auctionHouseModel.ItemImpl;
import shared.network.server.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerImpl extends UnicastRemoteObject implements Server {
	private final Item item;

	protected ServerImpl() throws RemoteException {
		this.item = new ItemImpl("123");
	}

	@Override
	public Item getItem(String itemID) throws RemoteException {
		return item;
	}
}
