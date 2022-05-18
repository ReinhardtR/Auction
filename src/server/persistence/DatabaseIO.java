package server.persistence;


import server.model.item.Item;

import java.sql.SQLException;
import java.util.ArrayList;


public interface DatabaseIO {

	Item getItem(String itemID) throws SQLException;

	ArrayList<Item> getAmountOfItems(int amount, String ascOrDesc) throws SQLException;

	void buyoutItemBought(Item item) throws SQLException;

	void updateAuctionOffer(Item item) throws SQLException;

}
