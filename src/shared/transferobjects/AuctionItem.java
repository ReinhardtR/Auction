package shared.transferobjects;

public class AuctionItem {

	private String title;
	private String description;
	private String tags;
	private int itemId;
	private double price;


	public AuctionItem(String title, String description, String tags, double starterPrice)
	{
		this.title = title;
		this.description = description;
		this.tags = tags;
		this.price = starterPrice;
	}
	public int getItemId() {
		return itemId;
	}

	/*
	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getTags() {
		return tags;
	}


	public Double getPrice() {
		return price;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	 */
}
