package server.model.temps;

import java.util.Date;

public class TempAuction implements TempSaleStrategy{

	private double bid;
	private String bidder;
	private Date endDate;


	public TempAuction(double bid, String bidder, Date endDate)
	{
		this.bid = bid;
		this.bidder = bidder;
		this.endDate = endDate;
	}


	@Override
	public void offer(double offer, String username) {
		this.bid = offer;
		this.bidder = username;
	}

	public String getCurrentBidder() {
		return bidder;
	}

	public double getCurrentBid() {
		return bid;
	}
}
