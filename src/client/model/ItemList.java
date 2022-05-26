package client.model;

import client.model.item.ItemCacheProxy;
import client.model.item.ObservableItem;
import shared.utils.PropertyChangeSubject;

import java.util.List;

public interface ItemList extends PropertyChangeSubject {

	List<ItemCacheProxy> getItemList();

	ObservableItem getCurrentlyViewedItem();

	void setCurrentlyViewedItemID(String itemID);
}
