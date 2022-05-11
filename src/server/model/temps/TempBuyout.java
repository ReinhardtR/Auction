package server.model.temps;

public class TempBuyout implements TempSaleStrategy {

	private double price;
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


}
