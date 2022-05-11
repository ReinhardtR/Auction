package client.model;

import client.network.LocalClient;
import shared.network.model.Item;
import shared.utils.PropertyChangeSubject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.util.HashMap;

public class ObservableItemListImpl implements ObservableItemList {
	private final LocalClient client;
	private final HashMap<String, ObservableItem> itemsForClient = new HashMap<>();

	public class ObservableItemListImpl implements ObservableItemList, PropertyChangeListener, PropertyChangeSubject {

		private final LocalClient client;
		private final HashMap<String, ObservableItem> itemsForClient = new HashMap<>();

		private final PropertyChangeSupport support;

		public ObservableItemListImpl(LocalClient client) {
			this.client = client;
			support = new PropertyChangeSupport(this);
		}

		@Override
		public ObservableItem getItemForAuction(String itemID) {
			ObservableItem observableItem = itemsForClient.get(itemID);

			if (observableItem == null) {
				try {
					Item item = client.getItem(itemID);
					observableItem = new ObservableItem(client, item);
					observableItem.addListener(itemID, this);
					itemsForClient.put(itemID, observableItem);
					System.out.println(itemID + " was put in");
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			return observableItem;
		}

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			support.firePropertyChange("model", null, evt.getNewValue());
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
