package client.views.auction;

import client.model.ObservableItem;
import client.model.ObservableItemList;
import client.model.ObservableItemListImpl;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import shared.network.model.Item;

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
		Item item = observableItemList.getItemForAuction("123");
		try {
			if(observableItemList.getCalculator().calculateNewOffer(item,offer))
			{
				System.out.printf("Higher offer smile :)");
				item.userSaleStrategy("123", offer, user);
			}
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
