package server.model;

public class Auction implements SaleStrategy {

	private int currentBid;
	private String currentBidder;
	private String auctionEndDate;


	@Override
	public void offer(int amount, String username) {

	}
}
