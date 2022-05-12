package server.model.item.SaleStrategy;

public class AuctionStrategy implements SaleStrategy {
	private int currentBid = 0;
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

	@Override
	public String strategyType() {
		return "Auction";
	}
}
