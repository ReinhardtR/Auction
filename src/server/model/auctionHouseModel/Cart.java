package server.model.auctionHouseModel;

import database.DatabaseAccess;
import database.DatabaseIO;
import shared.network.model.Item;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cart {

	private static final Lock lock = new ReentrantLock();
	private static Cart instance;
	private DatabaseIO database;
	private Item item;

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

	//TODO SKAL IMPLEMENTERES
	public Item getItem(int itemId) {
		return item;
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
	public void setItem(Item item) {
		this.item = item;
	}
}
