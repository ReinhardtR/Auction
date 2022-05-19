package server.persistence.item.select.salestrategy;

import server.model.item.SaleStrategy.SaleStrategy;
import server.persistence.utils.SQL;

import java.sql.Connection;
import java.sql.SQLException;

public interface SaleStrategySelector {
	SaleStrategy fetchStrategy(Connection c, String itemId, String saleStrategy) throws SQLException;
}
