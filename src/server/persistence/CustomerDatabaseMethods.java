package server.persistence;


import shared.network.model.Item;

import java.sql.SQLException;
import java.util.List;


public interface CustomerDatabaseMethods {

	Item getItem(String itemID) throws SQLException;

	List<Item> getAmountOfItems(int amount, String ascOrDesc) throws SQLException;

	void buyoutItemBought(Item item) throws SQLException;

	void updateAuctionOffer(Item item) throws SQLException;

}
