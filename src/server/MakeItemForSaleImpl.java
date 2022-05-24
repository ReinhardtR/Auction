package server;

import server.model.item.ItemImpl;
import server.model.item.SaleStrategy.AuctionStrategy;
import server.model.item.SaleStrategy.BuyoutStrategy;
import shared.SaleStrategyType;
import shared.network.model.Item;

import java.rmi.RemoteException;

public class MakeItemForSaleImpl implements MakeItemForSale {

	public MakeItemForSaleImpl() {
	}

	@Override
	public void makeItem(String title, String description, String tags, SaleStrategyType saleType, String username, double offer, String endtime) {
		try {
			if (saleType == SaleStrategyType.AUCTION) {
				Item item = new ItemImpl(username, title, description, tags, new AuctionStrategy(offer, "", null));
			} else if (saleType == SaleStrategyType.BUYOUT) {
				Item item = new ItemImpl(username, title, description, tags, new BuyoutStrategy(offer));
			}
		} catch (RemoteException e) {
			throw new RuntimeException();
		}
	}

	private void sendItemToDatabase() {
		System.out.println("DATA BASE KALD");
	}


}

