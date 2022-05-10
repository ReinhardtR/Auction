package client.model;

import shared.network.model.Item;

import java.util.HashMap;
import java.util.List;

public class ObservableItemListImpl implements ObservableItemList {


	private HashMap<String, Item> itemsForClient = new HashMap<>();


	@Override
	public Item getItemForAuction(String itemId) {
		if (itemsForClient.containsKey(itemId))
		{
			return itemsForClient.get(itemId);
		}
	}
}
