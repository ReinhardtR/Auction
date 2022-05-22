package client.views.itemlist;

import client.model.ObservableItem;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public interface ItemListViewModel extends PropertyChangeListener {

	ObservableList<ObservableItem> getObservableItemList();

	void openViewForItem(ObservableItem observableItem);

	void propertyChange(PropertyChangeEvent event);
}
