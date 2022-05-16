package client.model;

public class ItemCalculations {
	public static boolean isNewBidHigher(int offer, ObservableItem item) {
		return offer > item.getOfferAmount();
	}
}
