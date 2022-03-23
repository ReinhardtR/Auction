package shared.transferobjects;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AuctionBid implements Serializable {
	private final String itemId;
	private final LocalDateTime dateTime;
	private final String bidder;
	private final int amount;

	public AuctionBid(String itemId, String bidder, int amount, LocalDateTime dateTime) {
		this.itemId = itemId;
		this.bidder = bidder;
		this.amount = amount;
		this.dateTime = dateTime;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public String getBidder() {
		return bidder;
	}

	public int getAmount() {
		return amount;
	}

	public String getItemId() {
		return itemId;
	}

	public boolean isHigher(AuctionBid bid) {
		return amount > bid.getAmount();
	}
}
