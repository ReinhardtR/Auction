package server.softwarehouseacces.item.express.salestrategy;

import server.softwarehouseacces.temps.SaleStrategy;
import server.softwarehouseacces.temps.TempAuction;
import server.softwarehouseacces.temps.TempBuyout;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SaleStrategyExpress {
	public SaleStrategy fetchStrategy(Connection c, int itemID, String saleStrategy)
					throws SQLException {
		PreparedStatement saleStrategyStatement = c.prepareStatement(
						"SELECT * FROM \"public\"." +
										saleStrategy +
										" WHERE itemID = " + itemID);

		ResultSet saleStrategyResult = saleStrategyStatement.executeQuery();
		saleStrategyResult.next();

		SaleStrategy saleStrategyToReturn = null;

		if (saleStrategy.equalsIgnoreCase("auction")) {
			saleStrategyToReturn = auctionFetcher(saleStrategyResult);
		} else if (saleStrategy.equalsIgnoreCase("buyout")) {
			saleStrategyToReturn = buyoutFetcher(saleStrategyResult);
		}

		saleStrategyResult.close();

		return saleStrategyToReturn;
	}


	private SaleStrategy buyoutFetcher(ResultSet buyoutResult) throws SQLException {
		return new TempBuyout(Double.parseDouble(buyoutResult.getString("price")),
						buyoutResult.getString("buyer"),
						buyoutResult.getString("saleStrategy"));
	}


	private SaleStrategy auctionFetcher(ResultSet auctionResult) throws SQLException {
		return new TempAuction(Double.parseDouble(auctionResult.getString("currentBid")),
						auctionResult.getString("currentBidder"),
						auctionResult.getTimestamp("AuctionEndDate").toLocalDateTime(),
						auctionResult.getString("saleStrategy"));
	}
}
