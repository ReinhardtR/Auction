package client.model;

import shared.network.model.Item;

public interface ObservableItemList {

	Item getItemForAuction(String itemId);
	ItemCalculations getCalculator();

}
