package client.model;

import server.model.item.Cart;

public class ItemCalculations {
	public static boolean isNewBidHigher(double offer, ObservableItem item) {
		return offer > item.getOfferAmount();
	}

	public static boolean isItemSold(ObservableItem item)
	{
		return Cart.getInstance().getItem(item.getItemID()) == null;
	}
}
