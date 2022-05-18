package server.softwarehouse.utils.exceptions;

public class NonExistentOperator extends SQLUtilsException {
	public NonExistentOperator(String errorMessage) {
		super(errorMessage);
	}
}
