package server.model.item.SaleStrategy;

public interface SaleStrategy {

	void offer(int amount, String username);

	int getOfferAmount();

}
