package client.model;

import java.util.List;

public interface ObservableItemList {

	List<ObservableItem> getAllItemsFromServer();

	ObservableItem getCurrentlyViewedItem();

	void setCurrentlyViewedItem(String itemID);
}
