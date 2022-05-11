package client.model;

import client.network.LocalClient;
import server.model.broadcaster.UpdateBroadcasterImpl;
import shared.network.model.Item;
import shared.utils.PropertyChangeSubject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;

public class ObservableItem implements Item, PropertyChangeListener, PropertyChangeSubject {
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

	@Override
	public String getItemID() {
		return itemID;
	}

	@Override
	public void userSaleStrategy(String itemID, int amount, String username) {
		try {
			System.out.println("Kalder strategy fra mig the proxy til mit item på serverside");
			item.userSaleStrategy(itemID, amount, username);
			UpdateBroadcasterImpl.getBroadcasterInstance(itemID).broadcast(this);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
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
	public void propertyChange(PropertyChangeEvent evt) {
		System.out.println("MODEL FIRE");
		try {
			support.firePropertyChange(itemID, null, item.getOfferAmount());
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
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
