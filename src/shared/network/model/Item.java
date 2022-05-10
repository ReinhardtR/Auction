package shared.network.model;

public interface Item {
	String getItemID();

	void userSaleStrategy(int amount, String username);
}
