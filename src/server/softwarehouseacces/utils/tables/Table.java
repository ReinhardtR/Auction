package server.softwarehouseacces.utils.tables;

import server.softwarehouseacces.utils.exceptions.ColumnNonExistent;

import java.util.Locale;

public abstract class Table {
	protected String schemaName;
	protected String tableName;
	protected String[] columns;

	public String getSchema() {
		return schemaName;
	}

	public String getTableName() {
		return tableName;
	}

	public String getColumn(String columnName) throws ColumnNonExistent {
		String columnSearchingFor = columnName.strip().toLowerCase(Locale.ROOT);
		for (String column : columns) {
			if (column.equals(columnSearchingFor))
				return column;
		}

		throw new ColumnNonExistent("The column does either not exist in the database on table \""
						+ tableName + "\", or util.tables." + tableName +
						"is not updated.\nPlease check if column !\"" + columnSearchingFor + "\"! should exist in either");
	}

	public String[] getColumns() {
		return columns;
	}
}
