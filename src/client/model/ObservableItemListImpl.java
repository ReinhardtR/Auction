package client.model;

import client.network.LocalClient;
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
	private final HashMap<String, ObservableItem> items;
	private final LocalClient client;
	private String idForView;

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
	public List<ObservableItem> getAllItemsFromServer() {
		List<ObservableItem> observableItems = new ArrayList<>();

		try {
			for (Item item : client.getAllItems()) {
				ObservableItem observableItem = new ObservableItem(client, item);

				observableItems.add(observableItem);
				items.put(item.getItemID(), observableItem);
				System.out.println(observableItem.getItemID());
			}
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}

		return observableItems;
	}

	@Override
	public void setIdForView(String itemID) {
		idForView = itemID;
	}

	@Override
	public String getItemAndStrategy(ObservableItem observableItem) {
		return items.get(observableItem.getItemID()).getStrategy();
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
