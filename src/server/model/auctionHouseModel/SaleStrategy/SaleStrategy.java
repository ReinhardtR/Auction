package server.model.auctionHouseModel.SaleStrategy;

public interface SaleStrategy {

	void offer(int amount, String username);

	int getOfferAmount();

}
