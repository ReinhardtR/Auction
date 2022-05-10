package client.model.update_strategy;

import shared.network.model.Item;

public class UpdateAuctionStrategy implements UpdateItemStrategy {
	private final Item item;

	public UpdateAuctionStrategy(Item item) {
		this.item = item;
	}

	public void updateItem() {

	}
}
