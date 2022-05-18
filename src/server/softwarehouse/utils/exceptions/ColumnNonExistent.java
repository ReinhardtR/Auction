package server.softwarehouse.utils.exceptions;

public class ColumnNonExistent extends SQLUtilsException {
	public ColumnNonExistent(String errorMessage) {
		super(errorMessage);
	}
}
