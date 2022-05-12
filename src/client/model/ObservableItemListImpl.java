package client.model;

import client.network.LocalClient;
import shared.network.client.SharedClient;
import shared.network.model.Item;
import shared.utils.PropertyChangeSubject;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ObservableItemListImpl implements ObservableItemList, PropertyChangeSubject {
	private final PropertyChangeSupport support;
	private final HashMap<String, Item> items;
	private final LocalClient client;

	private Item currentlyViewedItem;

	public ObservableItemListImpl(LocalClient client) {
		support = new PropertyChangeSupport(this);
		items = new HashMap<>();

		this.client = client;
	}

	@Override
	public List<ObservableItem> getAllItemsFromServer() {
		List<ObservableItem> observableItemList = new ArrayList<>();

		try {
			for (Item item : client.getAllItems()) {
				items.put(item.getItemID(), item);
				observableItemList.add(new ObservableItem(client, item));
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return observableItemList;
	}

	@Override
	public ObservableItem getCurrentlyViewedItem() {
		try {
			return new ObservableItem(client, currentlyViewedItem);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return null; // TODO: throw exception?
	}

	@Override
	public void setCurrentlyViewedItem(String itemID) {
		if (currentlyViewedItem != null) {
			try {
				currentlyViewedItem.getUpdateBroadcaster().unregisterClient((SharedClient) client);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		currentlyViewedItem = getItem(itemID);

		try {
			currentlyViewedItem.getUpdateBroadcaster().registerClient((SharedClient) client);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	private Item getItem(String itemID) {
		Item item = items.get(itemID);

		if (item == null) {
			try {
				item = client.getItem(itemID);
				items.put(itemID, item);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		return item;
	}

	@Override
	public void addListener(String eventName, PropertyChangeListener listener) {
		support.addPropertyChangeListener(eventName, listener);
	}

	@Override
	public void removeListener(String eventName, PropertyChangeListener listener) {
		support.removePropertyChangeListener(eventName, listener);
	}
}