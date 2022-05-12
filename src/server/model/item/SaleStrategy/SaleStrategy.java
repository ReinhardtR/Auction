package server.model.item.SaleStrategy;

import shared.SaleStrategyType;
import shared.network.model.Item;

public interface SaleStrategy {

	void offer(Item item, int amount, String username);

	String getBuyer();

	int getOfferAmount();

	SaleStrategyType strategyType();
}
