package client.model;

public class ItemCalculations {
	public static boolean isNewBidHigher(double offer, ObservableItem item) {
		return offer > item.getOfferAmount();
	}
}
