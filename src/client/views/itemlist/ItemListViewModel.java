package client.views.itemlist;

import client.core.ViewHandler;
import client.model.ItemList;
import client.model.ObservableItem;
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

	public void openViewForItem(ObservableItem observableItem) {
		itemList.setCurrentlyViewedItem(observableItem.getItemID());

		SaleStrategyType strategyType = observableItem.strategyType();

		if (strategyType.equals(SaleStrategyType.AUCTION)) {
			ViewHandler.getInstance().openAuctionView();
		} else if (strategyType.equals(SaleStrategyType.BUYOUT)) {
			ViewHandler.getInstance().openBuyoutView();
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		System.out.println("EVENT: " + observableList.toString());
		observableList.removeIf((item) -> item.getItemID().equals(event.getNewValue()));
		System.out.println("EVENT: " + observableList.toString());
	}
}
