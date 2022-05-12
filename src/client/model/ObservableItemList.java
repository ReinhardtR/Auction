package client.model;

import java.util.List;

public interface ObservableItemList {

	ObservableItem getItem(String itemId);

	List<ObservableItem> getAllItemsFromServer();

	void setIdForView(String itemID);

	String getItemAndStrategy(ObservableItem observableItem);
}
