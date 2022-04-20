package client.views.auction;

import client.model.AuctionModel;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import shared.transferobjects.AuctionBid;

import java.beans.PropertyChangeEvent;
import java.time.LocalDateTime;

public class AuctionViewModel {
	private final AuctionModel auctionModel;
	private final StringProperty bidProperty;

	public AuctionViewModel(AuctionModel auctionModel) {
		bidProperty = new SimpleStringProperty();

		this.auctionModel = auctionModel;
		auctionModel.addListener("NEW_AUCTION_BID", this::onNewAuctionBid);
	}

	public void makeNewBid(String itemId, String bidder, int amount) {
		AuctionBid auctionBid = new AuctionBid(itemId, bidder, amount, LocalDateTime.now());
		auctionModel.makeNewBid(auctionBid);
	}

	private void onNewAuctionBid(PropertyChangeEvent event) {
		Platform.runLater(() -> {
			AuctionBid auctionBid = (AuctionBid) event.getNewValue();
			bidProperty.setValue(String.valueOf(auctionBid.getAmount()));
		});
	}

	public StringProperty bidProperty() {
		return bidProperty;
	}
}
