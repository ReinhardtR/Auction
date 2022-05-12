package client.model;

import shared.utils.PropertyChangeSubject;

import java.util.List;

public interface ObservableItemList extends PropertyChangeSubject {

	List<ObservableItem> getAllItemsFromServer();

	ObservableItem getCurrentlyViewedItem();

	void setCurrentlyViewedItem(String itemID);
}
