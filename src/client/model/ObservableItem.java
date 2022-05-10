package client.model;

import client.network.LocalClient;
import shared.network.model.Item;
import shared.utils.PropertyChangeSubject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ObservableItem implements Item, PropertyChangeListener, PropertyChangeSubject {
	private final PropertyChangeSupport support;
	private final Item item;
	private final String itemID;

	public ObservableItem(LocalClient client, Item item) {
		support = new PropertyChangeSupport(this);
		this.item = item;

		// Cache itemID
		itemID = item.getItemID();

		client.addListener(itemID, this);
	}

	@Override
	public String getItemID() {
		return itemID;
	}

	@Override
	public void userSaleStrategy(int amount, String username) {
		item.userSaleStrategy(amount, username);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		support.firePropertyChange(itemID, null, null);
	}

	@Override
	public void addListener(String eventName, PropertyChangeListener listener) {
		support.addPropertyChangeListener(eventName, listener);
	}

	@Override
	public void removeListener(String eventName, PropertyChangeListener listener) {
		support.addPropertyChangeListener(eventName, listener);
	}
}
