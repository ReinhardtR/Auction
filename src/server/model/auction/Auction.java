package server.model.auction;

import shared.transferobjects.AuctionBid;

public class Auction implements AuctionData {
	private final Item item;
	private AuctionBid bid;

	public Auction(Item item, AuctionBid bid) {
		this.item = item;
		this.bid = bid;
	}

	public AuctionBid getBid() {
		return bid;
	}

	@Override
	public void setBid(AuctionBid bid) {
		this.bid = bid;
	}

	public Item getItem() {
		return item;
	}
}
