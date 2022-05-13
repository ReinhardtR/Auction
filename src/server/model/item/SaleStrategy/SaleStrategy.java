package server.model.item.SaleStrategy;

import shared.SaleStrategyType;
import server.model.item.Item;

public interface SaleStrategy {

	void offer(Item item, int amount, String username);

	String getBuyer();

	int getOfferAmount();

	SaleStrategyType strategyType();
}
