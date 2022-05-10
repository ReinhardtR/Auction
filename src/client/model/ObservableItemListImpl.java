package client.model;

import client.network.LocalClient;
import shared.network.model.Item;

import java.rmi.RemoteException;
import java.util.HashMap;

public class ObservableItemListImpl implements ObservableItemList {


	private final LocalClient client;
	private final HashMap<String, ObservableItem> itemsForClient = new HashMap<>();


	public ObservableItemListImpl(LocalClient client) {
		this.client = client;
	}

	@Override
	public ObservableItem getItemForAuction(String itemID) {
		ObservableItem observableItem = itemsForClient.get(itemID);

		if (observableItem == null) {
			try {
				Item item = client.getItem(itemID);
				observableItem = new ObservableItem(client, item);
				itemsForClient.put(itemID, observableItem);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		return observableItem;
	}
}
