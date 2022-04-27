package server.model.auction;

import shared.network.client.AuctionData;
import shared.network.client.Client;
import shared.network.server.IAuctionManager;
import shared.transferobjects.AuctionBid;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class AuctionManager implements IAuctionManager {
	private final List<Client> watchingClients;
	private final Auction auction;

	public AuctionManager(Auction auction) {
		try {
			UnicastRemoteObject.exportObject(this, 0);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		this.auction = auction;
		watchingClients = new ArrayList<>();
	}

	public AuctionData getAuction() {
		return (AuctionData) auction;
	}

	@Override
	public void newAuctionBid(AuctionBid bid) throws RemoteException {
		System.out.println("MANAGER RECIEVES BID");
		System.out.println(getAuction().getBid().getAmount() + " " + bid.getAmount());
		if (!getAuction().getBid().isHigher(bid)) {
			System.out.println("NICE");
			getAuction().setBid(bid);
			broadcastOffer(bid);
		}
	}

	@Override
	public void registerClient(Client client) throws RemoteException {
		if (!watchingClients.contains(client)) {
			System.out.println("Has joined");
			watchingClients.add(client);
		}
	}

	private void broadcastOffer(AuctionBid bid) {
		System.out.println("Broadcasting Start");
		watchingClients.forEach((client -> {
			System.out.println("BROADCASTING");
			try {
				if (client == null) return;
				client.onNewBid(bid);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}));
	}
}