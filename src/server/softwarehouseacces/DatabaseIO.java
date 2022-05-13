package server.softwarehouseacces;

import server.softwarehouseacces.temps.Item;

import java.sql.SQLException;


public interface DatabaseIO {
	Item getItem(int itemID) throws SQLException;

	void buyoutItemBought(Item itemID) throws SQLException;

	void updateAuctionOffer(Item item) throws SQLException;

	void clearTable(String testTable) throws SQLException;
}
