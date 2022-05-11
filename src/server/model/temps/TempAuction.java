package server.model.temps;

import java.util.Date;

public class TempAuction implements TempSaleStrategy {

	private double bid;
	private String bidder;
	private Date endDate;
	private String saleStrategy;


	public TempAuction(double bid, String bidder, Date endDate, String saleStrategy) {
		this.bid = bid;
		this.bidder = bidder;
		this.endDate = endDate;
		this.saleStrategy = saleStrategy;
	}


	@Override
	public void offer(double offer, String username) {
		this.bid = offer;
		this.bidder = username;
	}


	@Override
	public String getUsernameFromBuyer() {
		return bidder;
	}


	@Override
	public double getOffer() {
		return bid;
	}

	public Date getEndDate() {
		return endDate;
	}

	public String getCurrentBidder() {
		return bidder;
	}

	public double getCurrentBid() {
		return bid;
	}

	@Override
	public String toString() {
		return "TempAuction{" +
						"bid=" + bid +
						", bidder='" + bidder + '\'' +
						", endDate=" + endDate +
						", saleStrategy='" + saleStrategy + '\'' +
						'}';
	}
}
