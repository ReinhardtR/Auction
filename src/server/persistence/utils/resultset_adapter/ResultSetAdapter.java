package server.persistence.utils.resultset_adapter;

import shared.network.model.Item;

import java.sql.Connection;
import java.util.List;

public interface ResultSetAdapter {


	Item fetchItem(Connection connection, String itemID);

	List<Item> fetchAmountOfItems(Connection connection, int amount, String ascOrDesc);
}
