package client.views.auction;

import client.model.ObservableItem;
import client.model.ObservableItemListImpl;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AuctionViewModel implements PropertyChangeListener {
	private final ObservableItem item;
	private final StringProperty itemText;
	private final IntegerProperty currentHighestBid;

	public AuctionViewModel(ObservableItemListImpl auctionModel) {
		String itemID = "123";
		this.item = auctionModel.getItemForAuction(itemID);

		itemText = new SimpleStringProperty();
		currentHighestBid = new SimpleIntegerProperty();
		item.addListener(itemID, this);
	}

	public void findItem() {
		itemText.setValue(item.getItemID());
	}

	public void bidOnItem(int offer) {
		item.userSaleStrategy(offer, "John");
	}

	public StringProperty propertyItemLabel() {
		return itemText;
	}

	public IntegerProperty propertyCurrentBid() {
		return currentHighestBid;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		System.out.println("change!");
		currentHighestBid.setValue(item.getOfferAmount());
	}
}
