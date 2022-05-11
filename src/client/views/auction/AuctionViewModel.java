package client.views.auction;

import client.model.ObservableItem;
import client.model.ObservableItemListImpl;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AuctionViewModel implements PropertyChangeListener {
	private final StringProperty itemText;
	private final IntegerProperty currentHighestBid;
	private final ObservableItem item;

	public AuctionViewModel(ObservableItemListImpl observableItemList) {
		itemText = new SimpleStringProperty();
		currentHighestBid = new SimpleIntegerProperty();

		item = observableItemList.getItem("123");
		item.addListener(item.getItemID(), this);
	}

	public void findItem() {
		itemText.setValue(item.getItemID());
	}

	public void bidOnItem(int offer) {
		System.out.println("BID VIEW MODEL: " + offer);
		item.userSaleStrategy(offer, "Reinhardt");
	}

	public StringProperty propertyItemLabel() {
		return itemText;
	}

	public IntegerProperty propertyCurrentBid() {
		return currentHighestBid;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		System.out.println("change!");
		Platform.runLater(() -> {
			currentHighestBid.setValue(item.getOfferAmount());
		});
	}
}
