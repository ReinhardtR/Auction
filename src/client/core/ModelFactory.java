package client.core;

import client.model.ItemList;
import client.model.ItemListImpl;
import client.model.User;
import client.model.UserImpl;

public class ModelFactory {
	private static final ModelFactory instance = new ModelFactory();
	private ItemList itemList;
	private User user;

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

	public User getUser() {
		if (user == null) {
			user = new UserImpl(ClientFactory.getInstance().getClient());
		}

		return user;
	}
}
