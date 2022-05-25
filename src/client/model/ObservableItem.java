package client.model;

import client.model.item.ItemCacheProxy;
import client.network.LocalClient;
import shared.SaleStrategyType;
import shared.utils.PropertyChangeSubject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.time.temporal.Temporal;

public class ObservableItem implements PropertyChangeListener, PropertyChangeSubject, ItemCacheProxy {
	private final PropertyChangeSupport support;
	private final ItemCacheProxy item;

	public ObservableItem(LocalClient client, ItemCacheProxy item) throws RemoteException {
		support = new PropertyChangeSupport(this);
		this.item = item;

		if (client != null) {
			client.addListener(this);
		}
	}

	@Override
	public String getItemID() {
		return item.getItemID();
	}

	@Override
	public String getSalesmanUsername() {
		return item.getSalesmanUsername();
	}

	@Override
	public String getTitle() {
		return item.getTitle();
	}

	@Override
	public String getDescription() {
		return item.getDescription();
	}

	@Override
	public String getTags() {
		return item.getTags();
	}

	@Override
	public boolean getIsSold() throws RemoteException {
		return item.getIsSold();
	}

	@Override
	public Temporal getEndTimestamp() {
		return item.getEndTimestamp();
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
		return item.getStrategyType();
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

	// TODO
	@Override
	public String getBuyerUsername() throws RemoteException {
		return null;
	}

	public void propertyChange(PropertyChangeEvent event) {
		// Only care about its own updates.
		System.out.println("Receive " + getItemID() + " " + event.getNewValue());
		if (!event.getOldValue().equals(getItemID())) return;

		System.out.println("Send");
		support.firePropertyChange(event);
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
