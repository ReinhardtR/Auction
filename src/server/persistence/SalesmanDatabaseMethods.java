package server.persistence;

import shared.network.model.Item;

import java.sql.SQLException;

public interface SalesmanDatabaseMethods {

	void addItemToDatabase(Item itemToAdd) throws SQLException;




}
