package client.core;

import client.model.ItemList;
import client.model.ItemListImpl;
import client.model.UsernameModel;
import client.model.UsernameModelImpl;

public class ModelFactory {
	private static final ModelFactory instance = new ModelFactory();
	private ItemListImpl itemList;
	private UsernameModelImpl usernameModel;

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

	public UsernameModel getUserNameModel() {
		if (usernameModel == null) {
			usernameModel = new UsernameModelImpl(ClientFactory.getInstance().getClient());
		}
		return usernameModel;
	}
}
