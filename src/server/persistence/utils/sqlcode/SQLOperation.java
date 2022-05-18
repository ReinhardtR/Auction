package server.persistence.utils.sqlcode;

import server.persistence.utils.exceptions.NonExistentOperator;

public class SQLOperation {

	public String make(String[][] sameConditionOnBothTables) throws NonExistentOperator {
		StringBuilder conditionToReturn = new StringBuilder();
		for (int i = 0; i < sameConditionOnBothTables.length; i++) {
			conditionToReturn.append(sameConditionOnBothTables[i][0])
							.append(" ").append(use(sameConditionOnBothTables[i][1]))
							.append(" ").append(sameConditionOnBothTables[i][2]);
			if (!(i == sameConditionOnBothTables.length - 1))
				conditionToReturn.append(" ").append(sameConditionOnBothTables[i][3]).append(" ");
		}
		return conditionToReturn.toString();
	}

	private String use(String operator) throws NonExistentOperator {
		if (!(operator.equals("=")
						|| operator.equals("!=")
						|| operator.equals("<>")
						|| operator.equals(">")
						|| operator.equals("<")
						|| operator.equals(">=")
						|| operator.equals("<=")
						|| operator.equals("!<")
						|| operator.equals("!>"))
		)
			throw new NonExistentOperator(
							"The operator !\"" + operator + "\"! is not recognised as a legel operator" +
											"\nPlease check if the operation is typed wrong, else update SQLStatements.operators"
			);
		return operator;
	}

}
