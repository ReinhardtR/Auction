package client.model;

import client.network.LocalClient;
import shared.SaleStrategyType;
import shared.network.model.Item;
import shared.utils.PropertyChangeSubject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.time.temporal.Temporal;

public class ObservableItem implements PropertyChangeListener, PropertyChangeSubject, Item {
	private final PropertyChangeSupport support;
	private final Item item;
	private final String itemID;
	private final Temporal endDateTime;
	private final SaleStrategyType strategyType;

	public ObservableItem(LocalClient client, Item item) throws RemoteException {
		support = new PropertyChangeSupport(this);
		this.item = item;

		// Cache
		itemID = item.getItemID();
		strategyType = item.getStrategyType();
		endDateTime = item.getEndTimestamp();

		if (client != null) {
			client.addListener(this);
		}
	}

	@Override
	public String getItemID() {
		return itemID;
	}

	@Override
	public boolean getIsSold() throws RemoteException {
		return item.getIsSold();
	}

	@Override
	public Temporal getEndTimestamp() {
		return endDateTime;
	}

	@Override
	public void userSaleStrategy(double amount, String username) {
		try {
			item.userSaleStrategy(amount, username);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public SaleStrategyType getStrategyType() {
		return strategyType;
	}

	@Override
	public double getOfferAmount() {
		try {
			return item.getOfferAmount();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return -1;
	}

	@Override
	public String getBuyerUsername() throws RemoteException {
		return null;
	}

	public void propertyChange(PropertyChangeEvent event) {
		// Only care about its own updates.
		System.out.println("Receive" + itemID + " " + event.getNewValue());
		if (!event.getNewValue().equals(itemID)) return;

		System.out.println("Send");
		support.firePropertyChange(event.getPropertyName(), null, itemID);
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
		support.addPropertyChangeListener(eventName, listener);
	}
}
