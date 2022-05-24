package server;

import shared.SaleStrategyType;

public interface MakeItemForSale {
	void makeItem(String title, String description, String tags, SaleStrategyType saleType, String username, double offer, String endtime);
}
