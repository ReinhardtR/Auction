package client.model;

import client.network.LocalClient;
import shared.network.model.Item;

import java.util.HashMap;

public class ObservableItemListImpl implements ObservableItemList {


	private final LocalClient client;
	private final HashMap<String, Item> itemsForClient = new HashMap<>();


	public ObservableItemListImpl(LocalClient client) {
		this.client = client;
	}

	@Override
	public Item getItemForAuction(String itemId) {
		if (itemsForClient.containsKey(itemId)) {

			return itemsForClient.get(itemId);
		} else {
			client
		}
	}
}
