package client.model;

import client.network.LocalClient;
import shared.network.model.Item;
import shared.utils.PropertyChangeSubject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;

public class ObservableItem implements PropertyChangeListener, PropertyChangeSubject {
	private final PropertyChangeSupport support;
	private final Item item;
	private final ItemCalculations itemCalculations;
	private final String itemID;

	public ObservableItem(LocalClient client, Item item) throws RemoteException {
		itemCalculations = new ItemCalculations();
		support = new PropertyChangeSupport(this);
		this.item = item;

		// Cache itemID
		itemID = item.getItemID();

		client.addListener(itemID, this);
	}

	public String getItemID() {
		return itemID;
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
