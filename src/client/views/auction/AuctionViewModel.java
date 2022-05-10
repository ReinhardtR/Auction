package client.views.auction;

import client.model.ObservableItem;
import client.model.ObservableItemListImpl;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;

public class AuctionViewModel implements PropertyChangeListener {
	private final ObservableItemListImpl auctionModel;

	private StringProperty itemText;
	private StringProperty currentHighestBid;

	public AuctionViewModel(ObservableItemListImpl auctionModel) {
		this.auctionModel = auctionModel;

		itemText = new SimpleStringProperty();
		currentHighestBid = new SimpleStringProperty();
		//auctionModel.addListener("itemChanges",this);
	}


	public void findItem()
	{
		String id = "1";
		//
		//MANUELT ITEMID FOR TESTING PURPOSESES
		//

		itemText.setValue(auctionModel.getItemForAuction(id).getItemID());
	}


	public StringProperty propertyItemLabel()
	{
		return itemText;
	}
	public StringProperty propertyCurrentBid()
	{
		return currentHighestBid;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

	}
}
