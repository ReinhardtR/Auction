package server.model.auction;

import shared.network.Auction;
import shared.network.server.AuctionHouse;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class AuctionHouseManager implements AuctionHouse {
	HashMap<String, Auction> auctions;

	public AuctionHouseManager() {
		try {
			UnicastRemoteObject.exportObject(this, 0);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		auctions = new HashMap<>();
	}

	public Auction getAuction(String itemId) throws Exception {
		System.out.println(itemId);
		Auction auction = auctions.get(itemId);

		if (auction == null) {
			throw new Exception("Couldn't find an Auction with the given item id");
		}

		return auction;
	}

	public HashMap<String, Auction> getAuctions() {
		return auctions;
	}

	public void addAuction(Auction auction) {
		try {
			auctions.put(auction.getItem().getId(), auction);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
