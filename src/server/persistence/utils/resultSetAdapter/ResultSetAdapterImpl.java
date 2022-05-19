package server.persistence.utils.resultSetAdapter;

import server.model.item.Item;
import server.model.item.ItemImpl;
import server.persistence.item.select.ItemSelector;
import server.persistence.item.select.ItemSelectorImpl;
import server.persistence.item.select.salestrategy.SaleStrategySelector;
import server.persistence.item.select.salestrategy.SaleStrategySelectorImpl;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ResultSetAdapterImpl implements ResultSetAdapter {

	ItemSelector itemSelector;
	SaleStrategySelector saleStrategySelector;


	public ResultSetAdapterImpl() {
		itemSelector = new ItemSelectorImpl();
		saleStrategySelector = new SaleStrategySelectorImpl();
	}



	@Override
	public Item fetchItem(Connection c, String itemID) {
		PreparedStatement statement = null;
		try {

			ResultSet itemResultSet = itemSelector.fetchItem(c,itemID, statement);
			if (itemResultSet == null) {
				statement.close();
				c.close();
				return null;
			}

			itemResultSet.next();

			Item itemtoReturn = itemCreation(c,itemResultSet);

			c.close();
			statement.close();


			return itemtoReturn;



	} catch (SQLException e) {
		e.printStackTrace();
	}

		return null;
	}



	@Override
	public ArrayList<Item> fetchAmountOfItems(Connection c, int amount, String ascOrDesc) {
		ArrayList<Item> itemsToReturn = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultset = null;


		try {
			resultset = itemSelector.fetchAmountOfItems(c, amount, ascOrDesc, statement);


		while(resultset.next())
		{
			itemsToReturn.add(itemCreation(c,resultset));
		}

		c.close();
		statement.close();
		} catch (SQLException e) {
		e.printStackTrace();
	}
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
		} catch (RemoteException e) {
			e.printStackTrace();
		}


		return itemToReturn;
	}








}

