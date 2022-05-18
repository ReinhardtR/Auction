package server.persistence.utils.sqlcode;

import server.persistence.utils.tables.Table;

public class SQLStatements {

	public String fetchRows(int amount) {
		return " FETCH FIRST " + amount + " ROWS ONLY";
	}

	public String orderBy(String columnToOrderBy, String ascOrDesc) {
		return " ORDER BY " + columnToOrderBy + " " + ascOrDesc;
	}

	public String selectAmount(Table[] tables, String[] columns, String columnToOrderBy, String ascOrDesc, int amount) {
		return "SELECT " + coalesce(tables, columns) + fromFullJoin() + orderBy(columnToOrderBy, ascOrDesc) + fetchRows(amount);
	}

	private String coalesce(Table[] tables, String[] columns) {
		if (tables.length < 1)
			return null;

		StringBuilder columnsToCoalesce = new StringBuilder();
		for (int i = 0; i < columns.length; i++) {
			columnsToCoalesce.append("COALESCE(");
			for (int j = 0; j < tables.length; j++) {
				columnsToCoalesce.append(tables[j].getTableName())
								.append(".")
								.append(columns[i]);
				if (!(j == tables.length - 1))
					columnsToCoalesce.append(", ");
			}
			columnsToCoalesce.append(") as ").append(columns[i]);
			if (!(i == columns.length - 1))
				columnsToCoalesce.append(", ");
		}

		return columnsToCoalesce.toString();
	}

	public String select(Table tableInUse, String[] columns, String conditions) {
		return "SELECT " + columns(columns) + from(tableInUse) + where(conditions);
	}


	public String update(Table tableInUse, String columnsToSet, String conditions) {
		return "UPDATE \"" + tableInUse.getSchema() + "\"." + tableInUse.getTableName() + set(columnsToSet) + where(conditions);
	}

	public String delete(Table tableInUse, String conditions) {
		return "DELETE \"" + tableInUse.getSchema() + "\"." + tableInUse.getTableName() + where(conditions);
	}

	public String union(Table[] tables, String[] columns, String sameConditionOnBothTables) {
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

	private String where(String sameConditionOnBothTables) {
		return " WHERE " + sameConditionOnBothTables;
	}

	private String set(String condition) {
		return " SET " + condition;
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
		return " FROM auction FULL JOIN buyout on auction.itemid = buyout.itemid"; //todo prøv at gøre dette IKKE hardcoded, har ikke fundet en metode til at joine flere tabeller endnu, i tilfælde der blev lavet fler.
	}
}
