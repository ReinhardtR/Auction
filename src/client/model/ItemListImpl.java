package client.model;

import client.model.item.ItemCacheProxy;
import client.model.item.ObservableItem;
import client.network.LocalClient;
import shared.EventType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemListImpl implements ItemList {
	private final PropertyChangeSupport support;
	private final HashMap<String, ItemCacheProxy> items;
	private final LocalClient client;
	private String currentlyViewedItemID;

	public ItemListImpl(LocalClient client) {
		this.client = client;

		support = new PropertyChangeSupport(this);
		items = new HashMap<>();

		try {
			for (ItemCacheProxy item : client.getAllItems()) {
				items.put(item.getItemID(), item);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		client.addListener(EventType.ITEM_SOLD.toString(), this::onItemSold);
	}

	@Override
	public List<ItemCacheProxy> getItemList() {
		return new ArrayList<>(items.values());
	}

	@Override
	public ObservableItem getCurrentlyViewedItem() {
		try {
			// Laver proxy af Item
			return new ObservableItem(client, items.get(currentlyViewedItemID));
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return null; // TODO: throw exception?
	}

	@Override
	public void setCurrentlyViewedItemID(String itemID) {
		currentlyViewedItemID = itemID;
	}

	@Override
	public void addListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}

	@Override
	public void removeListener(PropertyChangeListener listener) {
		support.removePropertyChangeListener(listener);
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
		System.out.println(event.getPropertyName());
		String itemID = (String) event.getOldValue();
		items.remove(itemID);
		support.firePropertyChange(EventType.ITEM_SOLD.toString(), null, itemID);
	}
}
