package server.softwarehouseacces.item.express;

import server.softwarehouseacces.item.express.salestrategy.SaleStrategyExpress;
import server.softwarehouseacces.temps.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemExpress {
	private final SaleStrategyExpress saleStrategyExpress;

	public ItemExpress() {
		saleStrategyExpress = new SaleStrategyExpress();
	}

	public Item fetchItem(Connection c, int itemID) throws SQLException {
		PreparedStatement itemStatement = c.prepareStatement(
						"SELECT itemID, saleStrategy FROM \"public\".Auction WHERE itemID = " + itemID +
										" UNION " +
										"SELECT itemID, saleStrategy FROM \"public\".Buyout WHERE itemID = " + itemID
		);
		ResultSet itemResultSet = itemStatement.executeQuery();

		if (itemResultSet == null) {
			itemStatement.close();
			c.close();
			return null;
		}

		itemResultSet.next();
		Item itemToReturn = new Item(
						itemResultSet.getInt("itemID"),
						saleStrategyExpress.fetchStrategy(
										c,
										itemResultSet.getInt("itemID"),
										itemResultSet.getString("saleStrategy")
						)
		);

		itemStatement.close();
		c.close();

		return itemToReturn;
	}
}