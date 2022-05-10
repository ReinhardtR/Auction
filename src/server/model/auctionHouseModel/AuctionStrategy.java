package server.model.auctionHouseModel;

public class AuctionStrategy implements SaleStrategy {
	private int currentBid;
	private String currentBidder;
	private String auctionEndDate;

	@Override
	public void offer(int amount, String username) {
		currentBid = amount;
		currentBidder = username;
	}

	@Override
	public int getOfferAmount() {
		return currentBid;
	}
}
