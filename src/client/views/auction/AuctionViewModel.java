package client.views.auction;

import client.model.ObservableItemList;
import client.model.ObservableItemListImpl;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;

public class AuctionViewModel implements PropertyChangeListener {
	private final StringProperty itemText;
	private final IntegerProperty currentHighestBid;
	private final ObservableItemList observableItemList;

	public AuctionViewModel(ObservableItemListImpl observableItemList) {
		itemText = new SimpleStringProperty();
		currentHighestBid = new SimpleIntegerProperty();
		this.observableItemList = observableItemList;
		observableItemList.addListener("model", this);


	}

	public void findItem() {
		String itemID = "123";

		try {
			itemText.setValue(observableItemList.getItemForAuction(itemID).getItemID());
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public void bidOnItem(int offer) {
		String user = "Reinhardt";
		try {
			observableItemList.getItemForAuction("123").userSaleStrategy("123", offer, "Reinhardt");
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
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
		currentHighestBid.setValue((int) evt.getNewValue());
	}
}
