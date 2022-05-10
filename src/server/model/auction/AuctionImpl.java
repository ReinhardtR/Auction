package server.model.auction;

import server.model.auctionHouseModel.SaleStrategy;

public class AuctionImpl implements SaleStrategy {

	private int currentBid;
	private String currentBidder;
	private String auctionEndDate;

	@Override
	public void offer(int amount, String username) {

	}
}
