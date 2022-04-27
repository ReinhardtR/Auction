package client.views.auction;

import client.model.AuctionModel;
import javafx.beans.property.ObjectProperty;
import shared.transferobjects.AuctionBid;

import java.time.LocalDateTime;

public class AuctionViewModel {
	private final AuctionModel auctionModel;

	public AuctionViewModel(AuctionModel auctionModel) {
		this.auctionModel = auctionModel;
	}

	public ObjectProperty<AuctionBid> bidProperty() {
		return auctionModel.bidProperty();
	}

	public void makeNewBid(String itemId, String bidder, int amount) {
		AuctionBid auctionBid = new AuctionBid(itemId, bidder, amount, LocalDateTime.now());
		auctionModel.makeNewBid(auctionBid);
	}
}
