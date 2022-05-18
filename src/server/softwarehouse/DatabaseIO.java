package server.softwarehouse;


import server.model.item.Item;

import java.sql.SQLException;


public interface DatabaseIO {

	Item getItem(String itemID) throws SQLException;

	Item[] getAmountOfItems(int amount,String ascOrDesc) throws SQLException;

	void buyoutItemBought(Item item) throws SQLException;

	void updateAuctionOffer(Item item) throws SQLException;

}
