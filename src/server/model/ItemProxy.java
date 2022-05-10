package server.model;

public class ItemProxy implements Item {
	private ItemImpl item;

	public ItemProxy(ItemImpl item) {
		this.item = item;
	}

	@Override
	public void userSaleStrategy(int amount, String username) {

	}
}
