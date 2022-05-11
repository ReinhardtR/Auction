package client.views.auctionsListe;

import client.model.ObservableItem;
import client.model.ObservableItemListImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AuctionsListeViewModel {

	private final ObservableItemListImpl observableItemListImpl;

	public AuctionsListeViewModel(ObservableItemListImpl observableItemList) {
		observableItemListImpl = observableItemList;
	}


	public ObservableList<ObservableItem> getObservableItemList() {
		return FXCollections.observableList(observableItemListImpl.getAllItemsFromServer());
	}
}
