package client.model.item;

import shared.SaleStrategyType;
import shared.network.model.Item;

import java.time.temporal.Temporal;

public interface ItemCacheProxy extends Item {
	String getItemID();

	String getSalesmanUsername();

	Temporal getEndTimestamp();

	SaleStrategyType getStrategyType();

	String getTitle();

	String getDescription();

	String getTags();
}
