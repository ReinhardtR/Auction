package server.softwarehouseacces.temps;

public interface SaleStrategy {
	void offer(double offer, String username);

	String getUsernameFromBuyer();

	double getOffer();

	String getSalesMethod();
}
