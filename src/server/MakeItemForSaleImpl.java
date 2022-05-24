package server;

import server.model.item.ItemImpl;
import shared.SaleStrategyType;
import shared.network.model.Item;

public class MakeItemForSaleImpl implements MakeItemForSale {

	public MakeItemForSaleImpl() {
	}

	@Override
	public void makeItem(String title, String description, String tags, SaleStrategyType saleType, String username, double offer, String endtime) {
		Item item = new ItemImpl()
	}
}

