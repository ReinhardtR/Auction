package client.views.buyout;

import client.model.ObservableItem;
import client.model.ObservableItemListImpl;

public class BuyoutViewModel {

	private final ObservableItem item;

	public BuyoutViewModel(ObservableItemListImpl observableItemList) {
		item = observableItemList.getItem("123");
	}
}
