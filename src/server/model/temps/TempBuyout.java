package server.model.temps;

public class TempBuyout implements TempSaleStrategy {

	private double price;
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


}
