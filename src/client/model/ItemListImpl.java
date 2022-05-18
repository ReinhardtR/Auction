package client.model;

import client.network.LocalClient;
import server.model.item.Item;
import shared.EventType;
import shared.network.client.SharedClient;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemListImpl implements ItemList {
	private final PropertyChangeSupport support;
	private final HashMap<String, Item> items;
	private final LocalClient client;
	private Item currentlyViewedItem;

	public ItemListImpl(LocalClient client) {
		support = new PropertyChangeSupport(this);
		items = new HashMap<>();

		this.client = client;
		client.addListener(EventType.ITEM_SOLD.toString(), this::onItemSold);
	}

	@Override
	public List<ObservableItem> getItemList() {
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
		// Unregister as listener to previously viewed item.
		if (currentlyViewedItem != null) {
			try {
				currentlyViewedItem.getUpdateBroadcaster().unregisterClient((SharedClient) client);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		currentlyViewedItem = getItem(itemID);

		// Register as listener to the new item being viewed.
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

	private void onItemSold(PropertyChangeEvent event) {
		String itemID = (String) event.getNewValue();
		items.remove(itemID);
		support.firePropertyChange(event.getPropertyName(), null, itemID);
	}
}
