package server.service.customer;

import server.persistence.CustomerDatabaseMethods;
import server.persistence.DatabaseAccess;
import shared.EventType;
import shared.network.model.Item;
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

public class CustomerItemService implements PropertyChangeSubject {
	private static final Lock lock = new ReentrantLock();
	private static CustomerItemService instance;
	private final PropertyChangeSupport support;
	private final HashMap<String, Item> items = new HashMap<>();
	private final CustomerDatabaseMethods database;

	private CustomerItemService() {
		support = new PropertyChangeSupport(this);
		database = new DatabaseAccess();
	}

	public static CustomerItemService getInstance() {
		if (instance == null) {
			synchronized (lock) {
				if (instance == null)
					instance = new CustomerItemService();
			}
		}

		return instance;
	}

	public List<Item> getAllStoredItems() {
		List<Item> itemsInCart = new ArrayList<>();

		items.forEach((itemID, item) -> {
			try {
				System.out.println(item.getItemID());
			} catch (RemoteException e) {
				throw new RuntimeException(e);
			}
			itemsInCart.add(item);
		});

		return itemsInCart;
	}

	public Item getItem(String itemId) {
		return items.get(itemId);
	}

	public void setItemAsBought(Item item) throws RemoteException, SQLException {
		database.buyoutItemBought(item);
		items.remove(item.getItemID());
		support.firePropertyChange(EventType.ITEM_SOLD.toString(), item.getItemID(), null);
	}

	public void clearAllItems() {
		items.clear();
	}

	public void updateItemOffer(Item item, Runnable callback) throws SQLException, RemoteException {
		database.updateAuctionOffer(item);

		callback.run();

		support.firePropertyChange(EventType.NEW_BID.toString(), item.getItemID(), item.getOfferAmount());
	}

	public void addItem(Item item) throws RemoteException {
		items.put(item.getItemID(), item);
	}

	public void getManyItems() throws RemoteException {
		try {
			for (Item item : database.getAmountOfItems(10, "asc")) {
				items.put(item.getItemID(), item);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

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
		support.removePropertyChangeListener(eventName, listener);
	}
}
