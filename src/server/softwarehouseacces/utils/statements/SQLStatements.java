package server.softwarehouseacces.utils.statements;

import server.softwarehouse.utils.tables.Table;

public class SQLStatements {

	public String fetchRows(int amount) {
		return " FETCH FIRST " + amount + " rows only";
	}

	public String orderBy(String columnToOrderBy,String ascOrDesc){
		return " ORDER BY " +columnToOrderBy+ " " + ascOrDesc;
	}

	public String selectAmount(Table[] tables, String columnToOrderBy , String ascOrDesc , int amount){

		return "SELECT " + coalesce(tables,columnToOrderBy) + fromFullJoin() + orderBy(columnToOrderBy,ascOrDesc)+ fetchRows(amount);
	}

	private String coalesce(Table[] tables, String columnToOrderBy) {
		if (tables.length < 1)
			return null;


		String[] tablesToCoalesce = new String[tables.length];
		for (int i = 0; i < tables.length; i++) {

			tablesToCoalesce[i] = tables[i].getTableName()+"."+columnToOrderBy;
		}

		StringBuilder columnsToCoalesce = new StringBuilder();

		for (int i = 0; i < tablesToCoalesce.length; i++) {
			columnsToCoalesce.append("COALESCE ( ");
			columnsToCoalesce.append(tablesToCoalesce[i]);
			columnsToCoalesce.append(") as ").append(columnToOrderBy);
			if (!(i == tablesToCoalesce.length - 1))
				columnsToCoalesce.append(", ");
		}

		return columnsToCoalesce.toString();
	}

	public String select(Table tableInUse, String[] columns, String[][] conditions) {
		return "SELECT " + columns(columns) + from(tableInUse) + where(conditions);
	}

	public String update(Table tableInUse, String[][] columnsToSet, String[][] conditions) {
		return "UPDATE \"" + tableInUse.getSchema() + "\"." + tableInUse.getTableName() + set(columnsToSet) + where(conditions);
	}

	public String delete(Table tableInUse, String[][] conditions) {
		return "DELETE \"" + tableInUse.getSchema() + "\"." + tableInUse.getTableName() + where(conditions);
	}

	public String union(Table[] tables, String[] columns, String[][] sameConditionOnBothTables) {
		StringBuilder unionToReturn = new StringBuilder();

		for (int i = 0; i < tables.length; i++) {
			unionToReturn.append(select(tables[i], columns, sameConditionOnBothTables));
			if (!(i == tables.length - 1))
				unionToReturn.append(" UNION ");
		}

		return unionToReturn.toString();
	}

	private String from(Table tableInUse) {
		return " FROM \"" + tableInUse.getSchema() + "\"." + tableInUse.getTableName();
	}

	private String where(String[][] sameConditionOnBothTables) {
		return " WHERE " + operationSetter(sameConditionOnBothTables);
	}

	private String set(String[][] condition) {
		return " SET " + operationSetter(condition);
	}

	private String columns(String[] columns) {
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

	private String fromFullJoin() {
		return " from auction full join buyout on auction.itemid = buyout.itemid"; //todo prøv at gøre dette IKKE hardcoded, har ikke fundet en metode til at joine flere tabeller endnu, i tilfælde der blev lavet fler.
	}

	private String operationSetter(String[][] sameConditionOnBothTables) {
		StringBuilder conditionToReturn = new StringBuilder();
		for (int i = 0; i < sameConditionOnBothTables.length; i++) {
			conditionToReturn
							.append(sameConditionOnBothTables[i][0])
							.append(" ").append(operators(sameConditionOnBothTables[i][1]))
							.append(" ").append(sameConditionOnBothTables[i][2]);
			if (!(i == sameConditionOnBothTables.length - 1))
				conditionToReturn.append(" ").append(sameConditionOnBothTables[i][3]).append(" ");
		}
		return conditionToReturn.toString();
	}

	private String operators(String operator) {
		if (!(operator.equals("=")
						| operator.equals("!=")
						| operator.equals("<>")
						| operator.equals(">")
						| operator.equals("<")
						| operator.equals(">=")
						| operator.equals("<=")
						| operator.equals("!<")
						| operator.equals("!>"))
		)
			return null;
		return operator;
	}
}
