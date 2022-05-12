package client.views.itemlist;

import client.model.ObservableItem;
import client.model.ObservableItemList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ItemListViewModel {

	private final ObservableItemList observableItemListImpl;

	public ItemListViewModel(ObservableItemList observableItemList) {
		observableItemListImpl = observableItemList;
	}

	public ObservableList<ObservableItem> getObservableItemList() {
		return FXCollections.observableList(observableItemListImpl.getAllItemsFromServer());
	}
}
