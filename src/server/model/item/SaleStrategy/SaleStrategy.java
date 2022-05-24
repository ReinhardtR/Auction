package server.model.item.SaleStrategy;

import shared.SaleStrategyType;
import shared.network.model.Item;

import java.time.temporal.Temporal;

public interface SaleStrategy {

	void offer(Item item, double amount, String username);

	String getBuyer();

	boolean getIsSold();

	double getOfferAmount();

	SaleStrategyType strategyType();

	Temporal getEndTime();
}
