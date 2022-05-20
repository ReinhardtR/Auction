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

	public String[] getCummonColumns(Table tableToCompare) {
		HashSet<String> toReturn = tableToCompare.getHashSet();
		toReturn.retainAll(columns);
		return toReturn.toArray(new String[0]);
	}

	private HashSet<String> getHashSet() {
		return (HashSet<String>) columns.clone();
	}
}
