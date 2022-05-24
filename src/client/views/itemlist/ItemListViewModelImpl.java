package client.views.itemlist;

import client.core.ViewHandler;
import client.model.ItemCacheProxy;
import client.model.ItemList;
import client.model.ObservableItem;
import client.utils.ViewEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shared.EventType;
import shared.SaleStrategyType;
import shared.network.model.Item;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;

public class ItemListViewModelImpl implements ItemListViewModel {
	private final ItemList itemList;
	private final ObservableList<ItemCacheProxy> observableList;

	public ItemListViewModelImpl(ItemList itemList) {
		this.itemList = itemList;

		observableList = FXCollections.observableList(itemList.getItemList());

		itemList.addListener(EventType.ITEM_SOLD.toString(), this::onItemSold);
	}

	@Override
	public ObservableList<ItemCacheProxy> getObservableItemList() {
		return observableList;
	}

	@Override
	public void openViewForItem(ItemCacheProxy item) {
		itemList.setCurrentlyViewedItemID(item.getItemID());

		SaleStrategyType strategyType = item.getStrategyType();

		if (strategyType.equals(SaleStrategyType.AUCTION)) {
			ViewHandler.getInstance().openView(ViewEnum.Auction, item.getItemID());
		} else if (strategyType.equals(SaleStrategyType.BUYOUT)) {
			ViewHandler.getInstance().openView(ViewEnum.Buyout, item.getItemID());
		}
	}

	private void onItemSold(PropertyChangeEvent event) {
		System.out.println("EVENT: LIST SIZE: " + observableList.size());
		observableList.removeIf((item) -> item.getItemID().equals(event.getNewValue()));
	}
}
