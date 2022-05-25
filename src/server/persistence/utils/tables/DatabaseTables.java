package server.persistence.utils.tables;

import server.persistence.utils.exceptions.TableNonExistent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class DatabaseTables {
	private final HashMap<String, Table> tablesOnDatabase;
	private final String fetchTableStatement;

	public DatabaseTables() {
		tablesOnDatabase = new HashMap<>();
		fetchTableStatement = "SELECT table_schema, table_name, column_name FROM information_schema.columns WHERE table_schema != 'pg_catalog' AND table_schema != 'information_schema' AND table_name != 'pg_stat_statements' ORDER BY table_name";
	}

	public void reconstructDatabaceTableLayout(Connection c) throws SQLException {
		ResultSet tableSet = c.prepareStatement(fetchTableStatement).executeQuery();
		String currentTableName = "";
		Table currentTable = null;
		while (tableSet.next()) {
			if (!tableSet.getString("table_name").equals(currentTableName)) {
				currentTableName = tableSet.getString("table_name");
				currentTable = new Table(tableSet.getString("table_schema"), currentTableName);
				tablesOnDatabase.put(currentTableName, currentTable);
			}
			currentTable.addColumn(tableSet.getString("column_name"));
		}
		c.close();
	}

	public Table getTable(String tableName) throws TableNonExistent {
		Table wantedTable = tablesOnDatabase.get(tableName.strip().toLowerCase());
		if (wantedTable == null) {
			throw new TableNonExistent(
							"The Table not exist on the database." +
											"\nPlease check if !\"" + tableName + "\"! should exists"
			);
		}
		return wantedTable;
	}
}
