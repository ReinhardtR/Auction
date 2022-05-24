package client.model;

import shared.SaleStrategyType;
import shared.network.model.Item;

import java.time.temporal.Temporal;

public interface ItemCacheProxy extends Item {
	String getItemID();
	Temporal getEndTimestamp();
	SaleStrategyType getStrategyType();
}
