package server.model.auction;

import shared.transferobjects.AuctionBid;

public interface Auction {
	Item getItem();

	void setBid(AuctionBid bid) throws Exception;
}
