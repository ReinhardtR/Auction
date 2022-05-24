package client.views.itemlist;

import client.model.ObservableItem;
import javafx.collections.ObservableList;

public interface ItemListViewModel {

	void openSaleView();

	ObservableList<ObservableItem> getObservableItemList();

	void openViewForItem(ObservableItem observableItem);
}
