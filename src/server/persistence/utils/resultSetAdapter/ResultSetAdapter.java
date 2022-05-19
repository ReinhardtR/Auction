package server.persistence.utils.resultSetAdapter;

import server.model.item.Item;
import server.model.item.ItemImpl;
import server.model.item.SaleStrategy.SaleStrategy;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

public interface ResultSetAdapter {


	Item fetchItem(Connection connection, String itemID);

	ArrayList<Item> fetchAmountOfItems(Connection connection, int amount, String ascOrDesc);
}
