package server.network;


import server.model.auctionHouseModel.Item;
import server.model.auctionHouseModel.ItemImpl;
import shared.network.server.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerImpl extends UnicastRemoteObject implements Server {
	private final Item item;

	public ServerImpl() throws RemoteException {
		this.item = new ItemImpl();
	}

	@Override
	public Item getItem(String itemID) throws RemoteException {
		return item;
	}
}
