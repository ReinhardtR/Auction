package server.model.auctionHouseModel.SaleStrategy;

public class BuyoutStrategy implements SaleStrategy {

	private int price;
	private String buyer;


	@Override
	public void offer(int amount, String username) {
		price = amount;
		buyer = username;
	}

	@Override
	public int getOfferAmount() {
		return price;
	}
}
