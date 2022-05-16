package server.softwarehouseacces.temps;

public class TempBuyout implements SaleStrategy {

	private double price; //Possibly ændre navn på dette for mere accuracy
	private String buyer;
	private String saleStrategy;


	public TempBuyout(double price, String buyer, String saleStrategy) {
		this.price = price;
		this.buyer = buyer;
		this.saleStrategy = saleStrategy;
	}


	@Override
	public void offer(double offer, String username) {
		this.price = offer;
		this.buyer = username;
	}

	@Override
	public String getUsernameFromBuyer() {
		return buyer;
	}

	@Override
	public double getOffer() {
		return price;
	}

	@Override
	public String getSalesMethod() {
		return saleStrategy;
	}


	public double getPrice() {
		return price;
	}

	public String getBuyer() {
		return buyer;
	}

	@Override
	public String toString() {
		return "TempBuyout{" +
						"price=" + price +
						", buyer='" + buyer + '\'' +
						", saleStrategy='" + saleStrategy + '\'' +
						'}';
	}
}
