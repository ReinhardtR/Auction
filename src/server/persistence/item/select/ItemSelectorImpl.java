package server.persistence.item.select;

import server.model.item.Item;
import server.persistence.item.select.salestrategy.SaleStrategySelectorImpl;
import server.persistence.utils.SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemSelectorImpl implements ItemSelector{

	public ItemSelectorImpl() {
	}

	public ResultSet fetchItem(Connection c, String itemID, PreparedStatement statement) throws SQLException {

		statement= c.prepareStatement(SQL.selectItem(itemID));


		return statement.executeQuery();


	}

	public ResultSet fetchAmountOfItems(Connection c, int amount, String ascOrDesc, PreparedStatement statement) throws SQLException {

		PreparedStatement amountOfItemsStatement = c.prepareStatement(SQL.selectAmountOfItems(amount, ascOrDesc));


		return amountOfItemsStatement.executeQuery();

	}


}
