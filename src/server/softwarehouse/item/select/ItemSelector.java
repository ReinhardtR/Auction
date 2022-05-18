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
		ItemImpl itemToReturn = itemCreation(c, itemResultSet);

		itemStatement.close();
		c.close();

		return itemToReturn;
	}

	public ArrayList<Item> fetchAmountOfItems(Connection c, int amount, String ascOrDesc) throws SQLException {
		ArrayList<Item> itemsToReturn = new ArrayList<>();

		PreparedStatement amountOfItemsStatement = c.prepareStatement(SQL.selectAmountOfItems(amount, ascOrDesc));
		ResultSet itemsToGetInformationFor = amountOfItemsStatement.executeQuery();

		while (itemsToGetInformationFor.next()) {
			itemsToReturn.add(itemCreation(c, itemsToGetInformationFor));
		}
		amountOfItemsStatement.close();
		c.close();
		return itemsToReturn;
	}

	private ItemImpl itemCreation(Connection c, ResultSet itemResultSet) throws SQLException {
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
			System.out.println(itemToReturn.getItemID() + " " + itemToReturn.getStrategyType());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return itemToReturn;
	}
}
