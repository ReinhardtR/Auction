package server.persistence.utils.tables;

import server.persistence.utils.exceptions.ColumnNonExistent;

import java.util.HashSet;
import java.util.Locale;

public class Table {
	private final String schemaName;
	private final String tableName;
	private final HashSet<String> columns;

	public Table(String schemaName, String tableName) {
		this.schemaName = schemaName;
		this.tableName = tableName;
		columns = new HashSet<>();
	}

	public String getSchema() {
		return schemaName;
	}

	public String getTableName() {
		return tableName;
	}

	public void addColumn(String column) {
		columns.add(column.strip().toLowerCase());
	}

	public String getColumn(String columnName) throws ColumnNonExistent {
		String columnSearchingFor = columnName.strip().toLowerCase(Locale.ROOT);
		if (columns.contains(columnSearchingFor))
			return columnSearchingFor;

		throw new ColumnNonExistent("The column does not exist on database in table \""
						+ tableName + "\".\nPlease check if column !\"" + columnSearchingFor + "\"! should exist");
	}

	public String[] getColumns() {
		return columns.toArray(new String[0]);
	}

	// Return a String[] that contains the column names of the columns
	// that both the given table and local table contains.
	public String[] getCommonColumns(Table tableToCompare) throws ColumnNonExistent {
		HashSet<String> toReturn = tableToCompare.getHashSet();
		toReturn.retainAll(columns);
		if (toReturn.isEmpty())
			throw new ColumnNonExistent("Table " + tableToCompare.getTableName() + " has no cummonColumns with " + tableName);
		return toReturn.toArray(new String[0]);
	}

	private HashSet<String> getHashSet() {
		return (HashSet<String>) columns.clone();
	}
}
