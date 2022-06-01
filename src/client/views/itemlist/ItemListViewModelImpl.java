package client.views.itemlist;

import client.core.ViewHandler;
import client.model.ItemList;
import client.model.item.ItemCacheProxy;
import client.utils.ViewEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shared.EventType;
import shared.SaleStrategyType;

import java.beans.PropertyChangeEvent;

public class ItemListViewModelImpl implements ItemListViewModel {
	private final ItemList itemList;
	private final ObservableList<ItemCacheProxy> observableItemList;

	public ItemListViewModelImpl(ItemList itemList) {
		this.itemList = itemList;

		observableItemList = FXCollections.observableList(itemList.getItemList());

		itemList.addListener(EventType.ITEM_SOLD.toString(), this::onItemSold);
	}

	@Override
	public void openSaleView() {
		ViewHandler.getInstance().openView(ViewEnum.Sale.toString());
	}

	@Override
	public ObservableList<ItemCacheProxy> getObservableItemList() {
		return observableItemList;
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

	// Remove item from table
	private void onItemSold(PropertyChangeEvent event) {
		observableItemList.removeIf((item) -> item.getItemID().equals(event.getNewValue()));
	}
}
