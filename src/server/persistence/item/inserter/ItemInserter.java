package server.persistence.item.inserter;

import server.model.item.ItemImpl;
import server.persistence.utils.SQL;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;

public class ItemInserter {


	//Grim metode, re-think ud fra tanken om at vi ikke ville lave if-else statements.
	public void addItemToDatabase(Connection connection, ItemImpl itemToAdd) throws SQLException {


		try {
			// brug enum ikke strings
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
		//Lave metode som bliver kaldt på database ud fra de informationer som ligger hos itemToAdd
	}


}
