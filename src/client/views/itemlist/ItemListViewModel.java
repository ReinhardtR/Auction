package client.views.itemlist;

import client.model.ObservableItem;
import client.model.ItemList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shared.SaleStrategyType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ItemListViewModel implements PropertyChangeListener {
	private final ItemList itemList;
	private final ObservableList<ObservableItem> observableList;

	public ItemListViewModel(ItemList itemList) {
		this.itemList = itemList;
		this.itemList.addListener("ITEM_SOLD", this);
		observableList = FXCollections.observableList(this.itemList.getAllItemsFromServer());
	}

	public ObservableList<ObservableItem> getObservableItemList() {
		return observableList;
	}

	public void setCurrentlyViewedItemID(String itemID) {
		itemList.setCurrentlyViewedItem(itemID);
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
