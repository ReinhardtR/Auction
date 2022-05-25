package client.views.itemlist;

import client.model.item.ItemCacheProxy;
import javafx.collections.ObservableList;

public interface ItemListViewModel {

	ObservableList<ItemCacheProxy> getObservableItemList();

	void openSaleView();

	void openViewForItem(ItemCacheProxy item);
}
