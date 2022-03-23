package server.model.auction;

import java.util.HashMap;

public class AuctionHouse {
	HashMap<String, Auction> auctions;

	public AuctionHouse() {
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
		auctions.put(auction.getItem().getId(), auction);
	}
}
