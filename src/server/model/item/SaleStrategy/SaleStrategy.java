package server.model.item.SaleStrategy;

import server.model.item.Item;
import shared.SaleStrategyType;

import java.time.temporal.Temporal;

public interface SaleStrategy {

	void offer(Item item, int amount, String username);

	String getBuyer();

	int getOfferAmount();

	SaleStrategyType strategyType();

	Temporal getEndTime();
}
