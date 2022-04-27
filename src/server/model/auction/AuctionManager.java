package server.model.auction;

import shared.network.Auction;
import shared.transferobjects.AuctionBid;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class AuctionManager implements Auction {
	private final Item item;
	ArrayList<Auction> bidders;
	private AuctionBid bid;

	public AuctionManager(Item item) {
		try {
			UnicastRemoteObject.exportObject(this, 0);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		bidders = new ArrayList<>();
		this.item = item;
		bid = new AuctionBid("123", "John", 0, LocalDateTime.now());
	}

	@Override
	public void newAuctionBid(AuctionBid auctionBid) throws RemoteException {
		if (!this.bid.isHigher(auctionBid)) {
			this.bid = auctionBid;
			broadcastOffer();
			return;
		}
		try {
			throw new Exception("New bid is not higher than current bid");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void joinAuction(Auction listener) throws RemoteException {
		if (!bidders.contains(listener)) {
			System.out.println("Has joined");
			bidders.add(listener);
		}
	}

	private void broadcastOffer() {
		for (Auction bidder : bidders) {
			try {
				bidder.newAuctionBid(this.bid);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}


	public Item getItem() {
		return item;
	}

	public AuctionBid getBid() {
		return bid;
	}
}