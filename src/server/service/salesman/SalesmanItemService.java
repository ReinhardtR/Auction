package server.service.salesman;

import shared.SaleStrategyType;

public interface SalesmanItemService {
	void makeItem(String title, String description, String tags, SaleStrategyType saleType, String username, double offer, String endtime);
}