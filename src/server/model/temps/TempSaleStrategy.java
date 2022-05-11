package server.model.temps;

public interface TempSaleStrategy {


	void offer(double offer, String username);

	String getUsernameFromBuyer();

	double getOffer();


}
