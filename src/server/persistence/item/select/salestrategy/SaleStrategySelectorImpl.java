package server.persistence.item.select.salestrategy;

import server.model.item.sale_strategy.AuctionStrategy;
import server.model.item.sale_strategy.BuyoutStrategy;
import server.model.item.sale_strategy.SaleStrategy;
import server.persistence.utils.SQL;
import shared.SaleStrategyType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SaleStrategySelectorImpl implements SaleStrategySelector {
	// Creates an instance of a SaleStrategy based on the given SaleStrategyType.
	// Constructs a SQL-statement that fetches the needed values to create the SaleStrategy.
	// Uses the two helper methods below to construct the SaleStrategy and the returns it.
	public SaleStrategy fetchStrategy(Connection c, String itemID, String saleStrategy) throws SQLException {

		PreparedStatement saleStrategyStatement = c.prepareStatement(SQL.selectSaleStrategy(itemID, saleStrategy));

		ResultSet saleStrategyResult = saleStrategyStatement.executeQuery();
		saleStrategyResult.next();

		SaleStrategy saleStrategyToReturn = null;

		if (saleStrategy.equals(SaleStrategyType.AUCTION.toString())) {
			saleStrategyToReturn = auctionFetcher(saleStrategyResult);
		} else if (saleStrategy.equals(SaleStrategyType.BUYOUT.toString())) {
			saleStrategyToReturn = buyoutFetcher(saleStrategyResult);
		}

		saleStrategyResult.close();

		return saleStrategyToReturn;
	}


	private SaleStrategy buyoutFetcher(ResultSet buyoutResult) throws SQLException {
		return new BuyoutStrategy(Double.parseDouble(buyoutResult.getString("price")));
	}


	private SaleStrategy auctionFetcher(ResultSet auctionResult) throws SQLException {
		return new AuctionStrategy(Double.parseDouble(auctionResult.getString("currentBid")),
						auctionResult.getString("currentBidder"),
						auctionResult.getTimestamp("AuctionEndDate").toLocalDateTime());
	}
}
