package server.persistence.utils.sql_code;

import server.persistence.utils.exceptions.NonExistentOperator;

public class SQLOperation {
	// Turns a 2D string array into a SQL-operation.
	// e.g.: itemID = "123"
	public String make(String[][] operations) throws NonExistentOperator {
		StringBuilder conditionToReturn = new StringBuilder();

		for (int i = 0; i < operations.length; i++) {
			conditionToReturn.append(operations[i][0])
							.append(" ").append(use(operations[i][1]))
							.append(" ").append(operations[i][2]);

			if (!(i == operations.length - 1))
				conditionToReturn.append(" ").append(operations[i][3]).append(" ");
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
