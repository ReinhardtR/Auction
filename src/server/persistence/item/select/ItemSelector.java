package server.persistence.item.select;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface ItemSelector {

	ResultSet fetchItem(Connection c, String itemID) throws SQLException;

	ResultSet fetchAmountOfItems(Connection c, int amount, String ascOrDesc) throws SQLException;


}
