package server.model.item;

import database.DatabaseAccess;
import database.DatabaseIO;
import shared.EventType;
import shared.utils.PropertyChangeSubject;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cart implements PropertyChangeSubject {
	private final PropertyChangeSupport support;
	private static final Lock lock = new ReentrantLock();
	private static Cart instance;
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
			try {
				itemsInCart.add(new ItemProxy(item));
			} catch (RemoteException e) {
				throw new RuntimeException(e);
			}
		});

		return itemsInCart;
	}

	//TODO SKAL IMPLEMENTERES
	public Item getItem(String itemId) {
		return items.get(itemId);
	}

	public void itemBought(Item item) {
		try {
			items.remove(item.getItemID());
			support.firePropertyChange(EventType.ITEM_SOLD.toString(), null, item.getItemID());
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}

		System.out.println("SOLD TO THE MAN IN BLUe");
	}

	public void updateItemOffer(Item item) {
		//DATABASE
		//ITEM UPDATED
	}

	//TIL TEST AF KØB USECASE
	public void addItem(Item item) throws RemoteException {
		items.put(item.getItemID(), item);
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
