package server.persistence;

import shared.network.model.Item;

import java.sql.SQLException;

public interface SalesManDatabaseMethods {

	void addItemToDatabase(Item itemToAdd) throws SQLException;


	



	void AlterItemOnDatabsae(String itemIDToAlter,String columnToAlter,String newValue) throws SQLException;


}
