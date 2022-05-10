package client.views.auction;

import client.model.ObservableItem;
import javafx.beans.property.ObjectProperty;
import shared.transferobjects.AuctionBid;

import java.time.LocalDateTime;

public class AuctionViewModel {
	private final ObservableItem auctionModel;

	public AuctionViewModel(ObservableItem auctionModel) {
		this.auctionModel = auctionModel;
	}

	//null returner
	public ObjectProperty<AuctionBid> bidProperty() {
		return null;
	}

	public void makeNewBid(String itemId, String bidder, int amount) {
		AuctionBid auctionBid = new AuctionBid(itemId, bidder, amount, LocalDateTime.now());
	}
}
