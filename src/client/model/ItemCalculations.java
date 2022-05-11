package client.model;


public class ItemCalculations {

	public static boolean calculateNewOffer(ObservableItem item, int offer) {
		return item.getOfferAmount() > offer;
	}

}
