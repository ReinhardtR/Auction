package server.model.auctionHouseModel;

public class AuctionImpl implements SaleStrategy {

	private int currentBid;
	private String currentBidder;
	private String auctionEndDate;
	private AuctioneerImpl auctioneer;

	@Override
	public void offer(int amount, String username) {

	}
}
