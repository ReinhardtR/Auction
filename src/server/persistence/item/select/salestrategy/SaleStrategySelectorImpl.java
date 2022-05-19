package server.persistence.item.select.salestrategy;

import server.model.item.SaleStrategy.AuctionStrategy;
import server.model.item.SaleStrategy.BuyoutStrategy;
import server.model.item.SaleStrategy.SaleStrategy;
import server.persistence.utils.SQL;
import shared.SaleStrategyType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SaleStrategySelectorImpl implements SaleStrategySelector{
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
