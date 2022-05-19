package server.persistence.item.select;

import server.persistence.utils.SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemSelectorImpl implements ItemSelector {

	public ItemSelectorImpl() {
	}

	public ResultSet fetchItem(Connection c, String itemID) throws SQLException {
		return c.prepareStatement(SQL.selectItem(itemID)).executeQuery();
	}

	public ResultSet fetchAmountOfItems(Connection c, int amount, String ascOrDesc) throws SQLException {
		return c.prepareStatement(SQL.selectAmountOfItems(amount, ascOrDesc)).executeQuery();
	}

}
