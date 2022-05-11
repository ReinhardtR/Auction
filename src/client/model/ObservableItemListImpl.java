package client.model;

import client.network.LocalClient;
import shared.network.model.Item;
import shared.utils.PropertyChangeSubject;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.util.HashMap;

public class ObservableItemListImpl implements ObservableItemList, PropertyChangeSubject {
	private final PropertyChangeSupport support;
	private final HashMap<String, ObservableItem> items;
	private final LocalClient client;

	public ObservableItemListImpl(LocalClient client) {
		support = new PropertyChangeSupport(this);
		items = new HashMap<>();

		this.client = client;
	}

	@Override
	public ObservableItem getItem(String itemID) {
		ObservableItem observableItem = items.get(itemID);

		if (observableItem == null) {
			try {
				Item item = client.getItem(itemID);

				observableItem = new ObservableItem(client, item);

				items.put(itemID, observableItem);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		return observableItem;
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
