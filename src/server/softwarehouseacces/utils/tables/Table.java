package server.softwarehouseacces.utils.tables;

import server.softwarehouseacces.utils.exceptions.ColumnNonExistent;

import java.util.ArrayList;
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

	public String getColum(String columnName) throws ColumnNonExistent {
		String columnSearchingFor = columnName.strip().toLowerCase(Locale.ROOT);
		for (String column : columns) {
			if (column.equals(columnSearchingFor))
				return column;
		}

		throw new ColumnNonExistent("The column does either not exist in the database on table \""
						+ tableName + "\", or util.tables." + tableName +
						"is not updated.\nPlease check if column !\"" + columnName + "\"! should exist in either");
	}

	public String[] getColumns(String[] columns) throws ColumnNonExistent {
		if (columns.length == 0)
			throw new ColumnNonExistent("No columns has been given");
		ArrayList<String> columnsToReturn = new ArrayList<>();
		for (String column : columns) {
			columnsToReturn.add(getColum(column));
		}
		return columnsToReturn.toArray(new String[0]);
	}

	public String[] getColumns() {
		return columns;
	}
/*
	public String[] allSharedColumns(Table tableTOCompare)
			throws ColumnNonExistent
	{
		String[] tableColumns = tableTOCompare.columns;
		ArrayList<String> columnSetToReturn = new ArrayList<>();
		for (String columnToCheck : tableColumns)
		{
			for (String column : columns)
			{
				if (columnToCheck.equals(column))
				{
					columnSetToReturn.add(columnToCheck);
					break;
				}
			}
		}
		if (columnSetToReturn.size()==0)
			throw new ColumnNonExistent("There are no Equel columns in table : "
					+ tableTOCompare.getTableName() + " and Table: " + tableName);
		return (String[]) columnSetToReturn.toArray();
	}
 */
}
