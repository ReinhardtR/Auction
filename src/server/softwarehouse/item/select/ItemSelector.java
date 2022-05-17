package server.softwarehouse.item.select;

import server.model.item.Item;
import server.model.item.ItemImpl;
import server.softwarehouse.item.select.salestrategy.SaleStrategySelector;
import server.softwarehouse.utils.SQL;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemSelector {
	private final SaleStrategySelector saleStrategySelector;

	public ItemSelector() {
		saleStrategySelector = new SaleStrategySelector();
	}

	public ItemImpl fetchItem(Connection c, String itemID) throws SQLException {

		PreparedStatement itemStatement = c.prepareStatement(SQL.selectItem(itemID));
		ResultSet itemResultSet = itemStatement.executeQuery();

		if (itemResultSet == null) {
			itemStatement.close();
			c.close();
			return null;
		}

		itemResultSet.next();
		ItemImpl itemToReturn = null;
		try {
			itemToReturn = new ItemImpl(
							itemResultSet.getString("itemID"),
							saleStrategySelector.fetchStrategy(
											c,
											itemResultSet.getString("itemID"),
											itemResultSet.getString("saleStrategy")
							)
			);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		itemStatement.close();
		c.close();

		return itemToReturn;
	}

	public Item[] fetchAmountOfItems(Connection c, int amount, String ascOrDesc) {
		ResultSet itemsToGetInformationFor = null;

		Item[] itemsToReturn = new Item[amount];
		try {
			PreparedStatement	amountOfItemsStatement = c.prepareStatement(SQL.selectAmountOfItems(amount,ascOrDesc));
			itemsToGetInformationFor = amountOfItemsStatement.executeQuery();




			for (int i = 0; i < itemsToGetInformationFor.getFetchSize(); i++) {
				itemsToGetInformationFor.next();
				itemsToReturn[i] = (fetchItem(c,itemsToGetInformationFor.getString("itemid")));

			}


			return itemsToReturn;

		} catch (SQLException e) {
			e.printStackTrace();
		}



		//Get amount of items,
		// gem dem i en array,
		// for hver item i array så kald fetch item metoden for at få den fulde information om item.

	}
}
