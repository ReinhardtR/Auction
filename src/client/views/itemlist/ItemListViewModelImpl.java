package client.views.itemlist;

import client.core.ViewHandler;
import client.model.ItemList;
import client.model.ObservableItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shared.EventType;
import shared.SaleStrategyType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ItemListViewModelImpl implements PropertyChangeListener, ItemListViewModel {
	private final ItemList itemList;
	private final ObservableList<ObservableItem> observableList;

	public ItemListViewModelImpl(ItemList itemList) {
		this.itemList = itemList;

		itemList.addListener(EventType.ITEM_SOLD.toString(), this);
		observableList = FXCollections.observableList(itemList.getItemList());
	}

	@Override
	public ObservableList<ObservableItem> getObservableItemList() {
		System.out.println("kalder i viewmodel");
		return observableList;
	}

	@Override
	public void openViewForItem(ObservableItem observableItem) {
		itemList.setCurrentlyViewedItem(observableItem.getItemID());

		SaleStrategyType strategyType = observableItem.getStrategyType();

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