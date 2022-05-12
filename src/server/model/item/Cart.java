package server.model.item;

import database.DatabaseAccess;
import database.DatabaseIO;
import shared.network.model.Item;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cart {

	private static final Lock lock = new ReentrantLock();
	private static Cart instance;
	private final HashMap<String, Item> items = new HashMap<>();
	private DatabaseIO database;

	private Cart() {
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

	public ArrayList<Item> returnAllItemsInCart() throws RemoteException {

		ArrayList<Item> itemsInCart = new ArrayList<>();

		itemsInCart.add(new ItemProxy(items.get("123")));

		return itemsInCart;
	}

	//TODO SKAL IMPLEMENTERES
	public Item getItem(String itemId) {
		return items.get(itemId);
	}

	public void itemBought(Item item) {
		//DATABASE KALD

		//ITEM SOLGT
	}

	public void updateItemOffer(Item item) {
		//DATABASE

		//ITEM UPDATED
	}


	//TIL TEST AF KØB USECASE
	public void setItem(Item item) throws RemoteException {
		items.put(item.getItemID(), item);
	}
}
