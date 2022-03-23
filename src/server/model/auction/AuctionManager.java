package server.model.auction;

import shared.transferobjects.AuctionBid;

import java.time.LocalDateTime;

public class AuctionManager implements Auction {
	private final Item item;
	private AuctionBid bid;

	public AuctionManager(Item item) {
		this.item = item;
		bid = new AuctionBid("123", "John", 0, LocalDateTime.now());
	}

	public Item getItem() {
		return item;
	}

	public AuctionBid getBid() {
		return bid;
	}

	public void setBid(AuctionBid bid) throws Exception {
		if (!this.bid.isHigher(bid)) {
			this.bid = bid;
			return;
		}

		throw new Exception("New bid is not higher than current bid");
	}
}
