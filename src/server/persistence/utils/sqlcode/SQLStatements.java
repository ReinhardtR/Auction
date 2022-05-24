package server.persistence.utils.sqlcode;

import server.persistence.utils.tables.Table;
import shared.SaleStrategyType;

import java.time.temporal.Temporal;

public class SQLStatements {
	public String select() {
		return "SELECT ";
	}

	public String select(String[] columns) {
		return select() + columns(columns);
	}

	public String select(Table tableInUse, String[] columns) {
		return select(columns) + from(tableInUse);
	}

	public String select(Table tableInUse, String[] columns, String condition) {
		return select(tableInUse, columns) + where(condition);
	}

	public String coalesce() {
		return "COALESCE";
	}

	public String coalesce(Table[] tables, String column) {
		return coalesce() + "(" + columnWithTableName(tables, column) + ")" + " as " + column;
	}

	public String coalesce(Table[] tables, String[] columns) {
		if (tables.length < 1)
			return null;

		StringBuilder columnsToCoalesce = new StringBuilder();
		for (int i = 0; i < columns.length; i++) {
			columnsToCoalesce.append(coalesce(tables, columns[i]));
			if (!(i == columns.length - 1))
				columnsToCoalesce.append(", ");
		}
		return columnsToCoalesce.toString();
	}

	public String coalesce(Table[] tables, String[] columns, String onColumn) {
		return coalesce(tables, columns) + fullJoin(tables, onColumn);
	}

	public String selectCoalesce(Table[] tables, String[] columns, String onColumn, String ascOrDesc, int nrOfRows) {
		return select() + coalesce(tables, columns, onColumn) + orderBy(onColumn, ascOrDesc) + fetchRows(nrOfRows);
	}

	public String union() {
		return " UNION ";
	}

	public String union(Table[] tables, String[] columns, String sameConditionOnBothTables) {
		StringBuilder unionToReturn = new StringBuilder();
		for (int i = 0; i < tables.length; i++) {
			unionToReturn.append(select(tables[i], columns, sameConditionOnBothTables));
			if (!(i == tables.length - 1))
				unionToReturn.append(union());
		}

		return unionToReturn.toString();
	}

	public String fullJoin() {
		return " FULL JOIN ";
	}

	public String fullJoin(Table tableJoining) {
		return fullJoin() + tableJoining.getTableName();
	}

	public String fullJoin(Table tableJoining, String onColumn, Table ontoTable) {
		return fullJoin(tableJoining) + on(tableJoining, onColumn, ontoTable);
	}

	public String fullJoin(Table[] tablesToJoin, String onColumn) {
		if (tablesToJoin.length < 2)
			return null;

		StringBuilder fullJoinToReturn = new StringBuilder().append(from(tablesToJoin[0]));
		for (int i = 1; i < tablesToJoin.length; i++) {
			fullJoinToReturn.append(fullJoin(tablesToJoin[i], onColumn, tablesToJoin[0]));
		}
		return fullJoinToReturn.toString();
	}

	public String on() {
		return " on ";
	}

	public String on(Table tableJoining, String onColumn, Table ontoTable) {
		return on() + columnWithTableName(ontoTable, onColumn) + " = " + columnWithTableName(tableJoining, onColumn);
	}


	public String where(String condition) {
		return " WHERE " + condition;
	}

	public String update(Table tableInUse, String columnsToSet, String conditions) {
		return "UPDATE \"" + tableInUse.getSchema() + "\"." + tableInUse.getTableName() + set(columnsToSet) + where(conditions);
	}

	public String delete(Table tableInUse, String conditions) {
		return "DELETE \"" + tableInUse.getSchema() + "\"." + tableInUse.getTableName() + where(conditions);
	}

	public String from(Table tableInUse) {
		return " FROM \"" + tableInUse.getSchema() + "\"." + tableInUse.getTableName();
	}

	public String set(String condition) {
		return " SET " + condition;
	}


	public String columns(String[] columns) {
		if (columns.length < 1)
			return null;

		StringBuilder columnsToReturn = new StringBuilder();
		for (int i = 0; i < columns.length; i++) {
			columnsToReturn.append(columns[i]);
			if (!(i == columns.length - 1))
				columnsToReturn.append(", ");
		}

		return columnsToReturn.toString();
	}

	public String columnWithTableName(Table table, String column) {
		return table.getTableName() + "." + column;
	}

	public String columnWithTableName(Table[] tables, String column) {
		StringBuilder columnsToReturn = new StringBuilder();
		for (int i = 0; i < tables.length; i++) {
			columnsToReturn.append(columnWithTableName(tables[i], column));
			if (!(i == tables.length - 1))
				columnsToReturn.append(", ");
		}
		return columnsToReturn.toString();
	}


	public String orderBy(String column, String ascOrDesc) {
		if ("asc".equalsIgnoreCase(ascOrDesc.strip()) || "desc".equalsIgnoreCase(ascOrDesc.strip()))
			return " ORDER BY " + column + " " + ascOrDesc;
		return "";
	}

	public String fetchRows(int amount) {
		return " FETCH FIRST " + amount + " ROWS ONLY";
	}


	public String insert(Table auction, double offerAmount, Temporal endTimestamp, String strategyType, String title, String tags, String description, String salesManUsername) {
		return "INSERT INTO " + auction.getSchema()+"." + auction.getTableName() + " VALUES " +
						"(DEFAULT," + offerAmount + "," + "null,TIMESTAMP(0) '" + endTimestamp + "','" + strategyType + "','"+title+"','"+tags+"','"+description+"','"+salesManUsername +"')";

	}

	public String insert(Table buyout, double offerAmount, String strategyType, String title, String tags, String description, String salesManUsername) {
	return "INSERT INTO " + buyout.getSchema()+"."+buyout.getTableName() + " VALUES " +
					"(DEFAULT," + offerAmount+",null,'"+ strategyType+"','"+description+"','"+tags+"','"+title+"','"+salesManUsername+"')";
	}
}