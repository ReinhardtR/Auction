package server.softwarehouseacces.utils.exceptions;

public class TableNonExistent extends Exception {
	public TableNonExistent(String errorMessage) {
		super(errorMessage);
	}
}
