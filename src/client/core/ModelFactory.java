package client.core;

import client.model.ObservableItemList;
import client.model.ObservableItemListImpl;

public class ModelFactory {
	private static final ModelFactory instance = new ModelFactory();
	private ObservableItemListImpl itemList;

	private ModelFactory() {
	}

	public static ModelFactory getInstance() {
		return instance;
	}

	public ObservableItemList getObservableItemList() {
		if (itemList == null) {
			itemList = new ObservableItemListImpl(ClientFactory.getInstance().getClient());
		}

		return itemList;
	}
}
