package shared.transferobjects;

public class Item {

	private String title;
	private String description;
	private String tags;
	private String itemId;
	private double starterPrice;

	public Item(String title, String description, String tags, double starterPrice)
	{
		this.title = title;
		this.description = description;
		this.tags = tags;
		this.starterPrice = starterPrice;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getTags() {
		return tags;
	}

	public String getItemId() {
		return itemId;
	}

	public Double getStarterPrice() {
		return starterPrice;
	}
}
