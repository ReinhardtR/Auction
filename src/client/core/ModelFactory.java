package client.core;

import client.model.ItemList;
import client.model.ItemListImpl;

public class ModelFactory {
	private static final ModelFactory instance = new ModelFactory();
	private ItemListImpl itemList;

	private ModelFactory() {
	}

	public static ModelFactory getInstance() {
		return instance;
	}

	public ItemList getObservableItemList() {
		if (itemList == null) {
			itemList = new ItemListImpl(ClientFactory.getInstance().getClient());
		}

		return itemList;
	}
}
