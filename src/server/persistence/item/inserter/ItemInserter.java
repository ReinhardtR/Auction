package server.persistence.item.inserter;

import server.model.item.ItemImpl;
import server.persistence.utils.SQL;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;

public class ItemInserter {
	// Use SQL helper class, to construct a SQL-statement that adds an item to the DB.
	// The statement is run through the given connection.
	// Closes connection after use.
	public void addItemToDatabase(Connection connection, ItemImpl itemToAdd) throws SQLException {
		try {
			if (itemToAdd.getStrategyType().toString().equalsIgnoreCase("auction")) {
				connection.prepareStatement(SQL.addAuctionItem(itemToAdd.getOfferAmount(), itemToAdd.getEndTimestamp(), itemToAdd.getStrategyType()
								, itemToAdd.getTitle(), itemToAdd.getTags(), itemToAdd.getDescription(), itemToAdd.getSalesmanUsername())).execute();
			} else if (itemToAdd.getStrategyType().toString().equalsIgnoreCase("buyout")) {
				connection.prepareStatement(SQL.addBuyoutItem(itemToAdd.getOfferAmount(), itemToAdd.getStrategyType()
								, itemToAdd.getTitle(), itemToAdd.getTags(), itemToAdd.getDescription(), itemToAdd.getSalesmanUsername())).execute();
			}
		} catch (RemoteException | SQLException e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}
}
