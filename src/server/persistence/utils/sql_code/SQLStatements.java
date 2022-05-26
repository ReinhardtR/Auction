package server.persistence.utils.sql_code;

import server.persistence.utils.exceptions.SQLUtilsException;
import server.persistence.utils.tables.Table;

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

	public String coalesce(Table[] tables, String column) throws SQLUtilsException {
		if (tables.length < 2)
			throw new SQLUtilsException("Min. Number of tables needed in coalesce is 2. Nr. of tables given: " + tables.length);
		return coalesce() + "(" + columnWithTableName(tables, column) + ")" + " as " + column;
	}

	public String coalesce(Table[] tables, String[] columns) throws SQLUtilsException {
		StringBuilder columnsToCoalesce = new StringBuilder();
		for (int i = 0; i < columns.length; i++) {
			columnsToCoalesce.append(coalesce(tables, columns[i]));
			if (!(i == columns.length - 1))
				columnsToCoalesce.append(", ");
		}
		return columnsToCoalesce.toString();
	}

	public String coalesce(Table[] tables, String[] columns, String onColumn) throws SQLUtilsException {
		return coalesce(tables, columns) + fullJoin(tables, onColumn);
	}

	public String selectCoalesce(Table[] tables, String[] columns, String onColumn, String ascOrDesc, int nrOfRows) throws SQLUtilsException {
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

	public String fullJoin(Table[] tablesToJoin, String onColumn) throws SQLUtilsException {
		if (tablesToJoin.length < 2)
			throw new SQLUtilsException("Needs at min. of two tables to perform operation. Given nr. of tables: " + tablesToJoin.length);

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
		return "UPDATE " + tableLocationAndName(tableInUse) + set(columnsToSet) + where(conditions);
	}

	public String delete(Table tableInUse, String conditions) {
		return "DELETE" + from(tableInUse) + where(conditions);
	}

	public String from() {
		return " FROM ";
	}

	public String from(Table tableInUse) {
		return from() + tableLocationAndName(tableInUse);
	}

	public String set(String condition) {
		return " SET " + condition;
	}

	public String values() {
		return " VALUES ";
	}

	public String values(String values) {
		return values() + "(" + values + ")";
	}

	public String insert() {
		return "INSERT INTO ";
	}

	public String insert(Table tableInUse) {
		return insert() + tableLocationAndName(tableInUse);
	}

	public String insert(Table tableInUse, String values) {
		return insert(tableInUse) + values(values);
	}

	public String orderBy(String column, String ascOrDesc) throws SQLUtilsException {
		if ("asc".equalsIgnoreCase(ascOrDesc.strip()) || "desc".equalsIgnoreCase(ascOrDesc.strip()))
			return " ORDER BY " + column + " " + ascOrDesc;
		throw new SQLUtilsException("Cannot ODER BY " + ascOrDesc + ". Needs to be either asc or desc");
	}

	public String fetchRows(int amount) {
		return " FETCH FIRST " + amount + " ROWS ONLY";
	}

	private String columns(String[] columns) {
		StringBuilder columnsToReturn = new StringBuilder();
		for (int i = 0; i < columns.length; i++) {
			columnsToReturn.append(columns[i]);
			if (!(i == columns.length - 1))
				columnsToReturn.append(", ");
		}

		return columnsToReturn.toString();
	}

	private String columnWithTableName(Table table, String column) {
		return table.getTableName() + "." + column;
	}

	private String columnWithTableName(Table[] tables, String column) {
		StringBuilder columnsToReturn = new StringBuilder();
		for (int i = 0; i < tables.length; i++) {
			columnsToReturn.append(columnWithTableName(tables[i], column));
			if (!(i == tables.length - 1))
				columnsToReturn.append(", ");
		}
		return columnsToReturn.toString();
	}

	private String tableLocationAndName(Table tableInUse) {
		return "\"" + tableInUse.getSchema() + "\"." + tableInUse.getTableName();
	}
}