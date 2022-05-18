package server.persistence.utils.exceptions;

public class ColumnNonExistent extends SQLUtilsException {
	public ColumnNonExistent(String errorMessage) {
		super(errorMessage);
	}
}
