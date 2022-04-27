package shared.network.client;

import server.model.auction.Item;
import shared.transferobjects.AuctionBid;

import java.io.Serializable;

public interface AuctionData extends Serializable {
	AuctionBid getBid();

	void setBid(AuctionBid bid);

	Item getItem();
}
