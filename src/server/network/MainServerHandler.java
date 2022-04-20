package server.network;

import server.model.auction.AuctionHouseManager;
import server.model.auction.AuctionManager;
import server.model.auction.Item;
import shared.network.Auction;
import shared.network.client.Client;
import shared.network.server.AuctionHouse;
import shared.network.server.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class MainServerHandler implements Server {

	AuctionHouseManager auctionHouse;

	List<Client> clients;

	public MainServerHandler() {
		try {
			UnicastRemoteObject.exportObject(this, 0);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		clients = new ArrayList<>();
		Auction auction = new AuctionManager(new Item("123"));
		auctionHouse = new AuctionHouseManager();
		auctionHouse.addAuction(auction);
	}

	@Override
	public AuctionHouse getActionHouse() throws RemoteException {
		return auctionHouse;
	}

	@Override
	public void registerAsClient(Client client) throws RemoteException {
		clients.add(client);
	}
}
