package client.model;

import client.network.LocalClient;
import shared.network.model.Item;
import shared.utils.PropertyChangeSubject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.time.temporal.Temporal;

public class ObservableItem implements PropertyChangeListener, PropertyChangeSubject {
	private final PropertyChangeSupport support;
	private final Item item;
	private final String itemID;
	private final Temporal endDateTime;

	public ObservableItem(LocalClient client, Item item) throws RemoteException {
		support = new PropertyChangeSupport(this);
		this.item = item;

		// Cache
		itemID = item.getItemID();
		endDateTime = item.getEndTimestamp();

		client.addListener(itemID, this);
	}

	public String getItemID() {
		return itemID;
	}

	public Temporal getEndDateTime() {
		return endDateTime;
	}

	public String getStrategy() {
		try {
			return item.strategyType();
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public void userSaleStrategy(int amount, String username) {
		System.out.println("MODEL: " + amount);
		try {
			item.userSaleStrategy(amount, username);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public int getOfferAmount() {
		try {
			return item.getOfferAmount();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return -1;
	}

	public void propertyChange(PropertyChangeEvent event) {
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
