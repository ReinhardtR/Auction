package server.persistence.utils.resultSetAdapter;

import server.model.item.ItemImpl;
import server.persistence.item.select.ItemSelector;
import server.persistence.item.select.ItemSelectorImpl;
import server.persistence.item.select.salestrategy.SaleStrategySelector;
import server.persistence.item.select.salestrategy.SaleStrategySelectorImpl;
import shared.network.model.Item;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResultSetAdapterImpl implements ResultSetAdapter {

	ItemSelector itemSelector;
	SaleStrategySelector saleStrategySelector;


	public ResultSetAdapterImpl() {
		itemSelector = new ItemSelectorImpl();
		saleStrategySelector = new SaleStrategySelectorImpl();
	}

	@Override
	public Item fetchItem(Connection c, String itemID) {
		Item itemToReturn = null;

		try {
			ResultSet itemResultSet = itemSelector.fetchItem(c, itemID);
			if (itemResultSet != null) {
				itemResultSet.next();
				itemToReturn = itemCreation(c, itemResultSet);
			}
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return itemToReturn;
	}

	@Override
	public List<Item> fetchAmountOfItems(Connection c, int amount, String ascOrDesc) {
		List<Item> itemsToReturn = new ArrayList<>();

		try {
			ResultSet resultset = itemSelector.fetchAmountOfItems(c, amount, ascOrDesc);

			if (resultset != null) {
				while (resultset.next()) {
					itemsToReturn.add(itemCreation(c, resultset));
				}
			}

			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return itemsToReturn;
	}


	private Item itemCreation(Connection c, ResultSet itemResultSet) throws SQLException {
		Item itemToReturn = null;

		try {
			itemToReturn = new ItemImpl(
							itemResultSet.getString("itemid"),
							itemResultSet.getString("salesmanusername"),
							itemResultSet.getString("title"),
							itemResultSet.getString("description"),
							itemResultSet.getString("tags"),
							saleStrategySelector.fetchStrategy(
											c,
											itemResultSet.getString("itemID"),
											itemResultSet.getString("saleStrategy")
							)
			);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		try {
			System.out.println(itemToReturn.getItemID()+"<- itemid "+itemToReturn.getTitle()+ itemToReturn.getTags()+itemToReturn.getDescription()+itemToReturn.getSalesManUsername());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return itemToReturn;
	}
}