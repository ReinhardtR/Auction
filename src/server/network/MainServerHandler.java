package server.network;

import server.model.auction.Auction;
import server.model.auction.AuctionHouse;
import server.model.auction.AuctionManager;
import server.model.auction.Item;
import shared.network.client.Client;
import shared.network.server.IAuctionHouse;
import shared.network.server.IAuctionManager;
import shared.network.server.Server;
import shared.transferobjects.AuctionBid;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MainServerHandler implements Server {

	AuctionHouse auctionHouse;

	List<Client> clients;

	public MainServerHandler() {
		try {
			UnicastRemoteObject.exportObject(this, 0);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		clients = new ArrayList<>();

		Auction auctionModel = new Auction(new Item("123"), new AuctionBid("123", "Jens", 0, LocalDateTime.now()));
		IAuctionManager auction = new AuctionManager(auctionModel);

		auctionHouse = new AuctionHouse();
		auctionHouse.addAuction(auction);
	}

	@Override
	public IAuctionHouse getActionHouse() throws RemoteException {
		return auctionHouse;
	}

	@Override
	public void registerAsClient(Client client) throws RemoteException {
		clients.add(client);
	}
}
