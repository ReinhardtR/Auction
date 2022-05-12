package client.views.buyout;

import client.model.ObservableItem;
import client.model.ObservableItemList;

public class BuyoutViewModel {
	private final ObservableItem item;

	public BuyoutViewModel(ObservableItemList itemList) {
		item = itemList.getItem("456");
	}

	public void onBuy(int amount, String username) {
		item.userSaleStrategy(amount, username);
	}
}
