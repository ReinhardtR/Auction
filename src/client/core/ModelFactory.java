package client.core;

import client.model.ObservableItemListImpl;
import client.network.LocalClient;

public class ModelFactory {
	private static final ModelFactory instance = new ModelFactory();
	private ObservableItemListImpl itemList;

	private ModelFactory() {
	}

	public static ModelFactory getInstance() {
		return instance;
	}


	public ObservableItemListImpl getAuctionModelTest() {
		if (itemList == null) {
			LocalClient client = ClientFactory.getInstance().getClient();
			itemList = new ObservableItemListImpl(client);
		}

		return itemList;
	}
}
