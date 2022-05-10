package client.views.auction;

import client.model.ObservableItemListImpl;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AuctionViewModel implements PropertyChangeListener {
	private final ObservableItemListImpl auctionModel;
	private final StringProperty itemText;
	private final StringProperty currentHighestBid;

	public AuctionViewModel(ObservableItemListImpl auctionModel) {
		this.auctionModel = auctionModel;

		itemText = new SimpleStringProperty();
		currentHighestBid = new SimpleStringProperty();

		
	}

	public void findItem() {
		String id = "123";
		//
		//MANUELT ITEMID FOR TESTING PURPOSESES
		//

		itemText.setValue(auctionModel.getItemForAuction(id).getItemID());
	}


	public StringProperty propertyItemLabel() {
		return itemText;
	}

	public StringProperty propertyCurrentBid() {
		return currentHighestBid;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

	}
}
