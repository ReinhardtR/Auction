package client.core;

import client.model.ObservableItem;
import client.network.LocalClient;

import java.rmi.RemoteException;

public class ModelFactory {
	private static final ModelFactory instance = new ModelFactory();
	private ObservableItem auctionModel;

	private ModelFactory() {
	}

	public static ModelFactory getInstance() {
		return instance;
	}

	public ObservableItem getAuctionModelTest() {
		if (auctionModel == null) {
			LocalClient client = ClientFactory.getInstance().getClient();
				auctionModel = new ObservableItem(client, null);
		}

		return auctionModel;
	}
}
