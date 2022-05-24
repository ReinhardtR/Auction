package server.model.item;

import server.persistence.DatabaseAccess;
import server.persistence.CustomerDatabaseMethods;
import shared.EventType;
import shared.network.model.Item;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cart {
	private static final Lock lock = new ReentrantLock();
	private static Cart instance;
	private final PropertyChangeSupport support;
	private final HashMap<String, Item> items = new HashMap<>();
	private final CustomerDatabaseMethods database;

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

	public void itemBought(Item item) throws RemoteException, SQLException {
		database.buyoutItemBought(item);
		items.remove(item.getItemID());
		support.firePropertyChange(EventType.ITEM_SOLD.toString(), item.getItemID(), null);
		System.out.println("SOLD TO THE MAN IN BLUe");
	}

	public void clearAllItems() {
		items.clear();
	} //Midlertidig

	public void updateItemOffer(Item item, Runnable callback) throws SQLException, RemoteException {
		database.updateAuctionOffer(item);

		callback.run();

		support.firePropertyChange(EventType.NEW_BID.toString(), item.getItemID(), item.getOfferAmount());
		System.out.println("Bid went through");
	}

	//TIL LAV ITEM USECASE
	public void addItem() throws RemoteException {

		//manuelt for testing

		//FØR DATABASE MERGE
		//items.put(item.getItemID(), item);
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

	public void addListenerToAllEvents(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}
}
