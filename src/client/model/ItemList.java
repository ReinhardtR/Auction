package client.model;

import shared.utils.PropertyChangeSubject;

import java.util.List;

public interface ItemList extends PropertyChangeSubject {

	List<ObservableItem> getAllItemsFromServer();

	ObservableItem getCurrentlyViewedItem();

	void setCurrentlyViewedItem(String itemID);
}
