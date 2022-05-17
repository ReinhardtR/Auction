package server.model.item;

import server.softwarehouseacces.DatabaseAccess;
import server.softwarehouseacces.DatabaseIO;
import shared.EventType;
import shared.utils.PropertyChangeSubject;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cart implements PropertyChangeSubject {
	private static final Lock lock = new ReentrantLock();
	private static Cart instance;
	private final PropertyChangeSupport support;
	private final HashMap<String, Item> items = new HashMap<>();
	private final DatabaseIO database;

	private Cart() {
		support = new PropertyChangeSupport(this);
		database = new DatabaseAccess();
	}

	public static Cart getInstance() {
		if (instance == null) {
			synchronized (lock) {
				if (instance == null)
					instance = new Cart();
			}
		}

		return instance;
	}

	public List<Item> returnAllItemsInCart() {
		List<Item> itemsInCart = new ArrayList<>();

		items.forEach((itemID, item) -> {
			itemsInCart.add(item);
		});

		return itemsInCart;
	}

	//TODO SKAL IMPLEMENTERES
	public Item getItem(String itemId) {


		return items.get(itemId);
	}

	public void itemBought(Item item) throws RemoteException {


		//FØR DATABASE MERGE
		database.buyoutItemBought(items.get(item.getItemID()));
		items.remove(item.getItemID());
		support.firePropertyChange(EventType.ITEM_SOLD.toString(), null, item.getItemID());
		System.out.println("SOLD TO THE MAN IN BLUe");
	}

	public void clearAllItems() {
		items.clear();
	} //Midlertidig

	public void updateItemOffer(Item item) {
		database.updateAuctionOffer(item);
	}

	//TIL TEST AF KØB USECASE
	public void addItem(Item item) throws SQLException {

		//manuelt for testing
		items.put("1", database.getItem(1));
		items.put("2", database.getItem(2));


		//FØR DATABASE MERGE
		//items.put(item.getItemID(), item);
	}

	public void addListenerToAllEvents(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
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
