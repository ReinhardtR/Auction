package client.model;


import shared.network.model.Item;

import java.rmi.RemoteException;

public class ItemCalculations {

	public boolean calculateNewOffer(Item item, int offer)
	{
		try {
			return item.getOfferAmount() > offer;
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

}
