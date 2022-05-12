package client.views.itemlist;

import client.model.ObservableItem;
import client.model.ObservableItemList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shared.SaleStrategyType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ItemListViewModel implements PropertyChangeListener {
	private final ObservableItemList observableItemList;
	private final ObservableList<ObservableItem> observableList;

	public ItemListViewModel(ObservableItemList observableItemList) {
		this.observableItemList = observableItemList;
		this.observableItemList.addListener("ITEM_SOLD", this);
		observableList = FXCollections.observableList(this.observableItemList.getAllItemsFromServer());
	}

	public ObservableList<ObservableItem> getObservableItemList() {
		return observableList;
	}

	public void setCurrentlyViewedItemID(String itemID) {
		observableItemList.setCurrentlyViewedItem(itemID);
	}

	public SaleStrategyType getStrategyOnItem(ObservableItem item) {
		return item.getSaleStrategyType();
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		System.out.println("EVENT: " + observableList.toString());
		observableList.removeIf((item) -> item.getItemID().equals(event.getNewValue()));
		System.out.println("EVENT: " + observableList.toString());
	}
}
