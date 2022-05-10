package server.model.auctionHouseModel;

public interface SaleStrategy {

	void offer(int amount, String username);

	int getOfferAmount();

}
