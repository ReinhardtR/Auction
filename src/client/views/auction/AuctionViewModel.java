package client.views.auction;

import client.model.ObservableItem;
import javafx.beans.property.ObjectProperty;

import java.time.LocalDateTime;

public class AuctionViewModel {
	private final ObservableItem auctionModel;

	public AuctionViewModel(ObservableItem auctionModel) {
		this.auctionModel = auctionModel;
	}

	//null returner
}
