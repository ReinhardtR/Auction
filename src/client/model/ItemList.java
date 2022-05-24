package client.model;

import shared.utils.PropertyChangeSubject;

import java.util.List;

public interface ItemList extends PropertyChangeSubject {

	List<ItemCacheProxy> getItemList();

	ObservableItem getCurrentlyViewedItem();

	void setCurrentlyViewedItemID(String itemID);
}
