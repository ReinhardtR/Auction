package server.softwarehouseacces.item.express;

import server.model.item.ItemImpl;
import server.softwarehouseacces.item.express.salestrategy.SaleStrategyExpress;
import server.softwarehouseacces.utils.SQL;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemExpress {
	private final SaleStrategyExpress saleStrategyExpress;

	public ItemExpress() {
		saleStrategyExpress = new SaleStrategyExpress();
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
							saleStrategyExpress.fetchStrategy(
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
}
