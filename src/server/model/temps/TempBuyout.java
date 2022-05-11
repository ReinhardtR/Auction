package server.model.temps;

public class TempBuyout implements TempSaleStrategy {

	private double price; //Possibly ændre navn på dette for mere accuracy
	private String buyer;


	public TempBuyout(double price, String buyer){
		this.price = price;
		this.buyer = buyer;
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


	public double getPrice() {
		return price;
	}

	public String getBuyer() {
		return buyer;
	}
}
