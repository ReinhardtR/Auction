package client.views.itemlist;

import client.model.ItemCacheProxy;
import client.model.ObservableItem;
import javafx.collections.ObservableList;
import shared.network.model.Item;

public interface ItemListViewModel {

	ObservableList<ItemCacheProxy> getObservableItemList();

	void openSaleView();

	void openViewForItem(ItemCacheProxy item);
}
