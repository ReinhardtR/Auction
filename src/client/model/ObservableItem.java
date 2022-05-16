package client.model;

import client.network.LocalClient;
import server.model.item.Item;
import shared.EventType;
import shared.SaleStrategyType;
import shared.network.model.GenerelItems;
import shared.utils.PropertyChangeSubject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.time.temporal.Temporal;

public class ObservableItem implements PropertyChangeListener, PropertyChangeSubject, GenerelItems {
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

		if (client != null) {
			client.addListener(EventType.NEW_BID.toString() + itemID, this);
		}
	}


	@Override
	public String getItemID() {
		return itemID;
	}


	@Override
	public Temporal getEndTimestamp() {
		return endDateTime;
	}

	@Override
	public void userSaleStrategy(int amount, String username) {

		if (ItemCalculations.isNewBidHigher(amount, this)) {
			System.out.println("MODEL: " + amount);
			try {
				item.userSaleStrategy(amount, username);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public SaleStrategyType strategyType() {
		try {
			return item.strategyType();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public int getOfferAmount() {
		try {
			return item.getOfferAmount();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return -1;
	}

	@Override
	public void setAsSold() throws RemoteException {

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
