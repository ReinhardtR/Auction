package client.model;


public class ItemCalculations {

	public static boolean isCurrentBidHigher(ObservableItem item, int offer) {
		return item.getOfferAmount() > offer;
	}

}
