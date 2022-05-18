package server.softwarehouse.utils.exceptions;

public class TableNonExistent extends SQLUtilsException {
	public TableNonExistent(String errorMessage) {
		super(errorMessage);
	}
}
