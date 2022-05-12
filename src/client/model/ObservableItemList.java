package client.model;

import java.util.List;

public interface ObservableItemList {

	ObservableItem getItem(String itemId);

	List<ObservableItem> getAllItemsFromServer();

	String getIDForView();

	void setIDForView(String itemID);

}
