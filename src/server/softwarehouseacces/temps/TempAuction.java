package server.softwarehouseacces.temps;

import java.time.LocalDateTime;

public class TempAuction implements SaleStrategy {

	private double bid;
	private String bidder;
	private LocalDateTime endDate;
	private String saleStrategy;


	public TempAuction(double bid, String bidder, LocalDateTime endDate, String saleStrategy) {
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

	@Override
	public String getSalesMethod() {
		return saleStrategy;
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
