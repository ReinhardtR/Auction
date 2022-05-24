package server;

import server.model.item.ItemImpl;
import server.model.item.SaleStrategy.AuctionStrategy;
import server.model.item.SaleStrategy.BuyoutStrategy;
import server.persistence.DatabaseAccess;
import server.persistence.SalesManDatabaseMethods;
import shared.SaleStrategyType;
import shared.network.model.Item;

import java.rmi.RemoteException;
import java.sql.SQLException;

public class MakeItemForSaleImpl implements MakeItemForSale {

	private final SalesManDatabaseMethods salesManDatabaseMethods;

	public MakeItemForSaleImpl() {
		salesManDatabaseMethods = new DatabaseAccess();
	}

	@Override
	public void makeItem(String title, String description, String tags, SaleStrategyType saleType, String username, double offer, String endtime) {
		try {
			Item item = null;
			if (saleType == SaleStrategyType.AUCTION) {
				item = new ItemImpl(username, title, description, tags, new AuctionStrategy(offer, "", null));
			} else if (saleType == SaleStrategyType.BUYOUT) {
				item = new ItemImpl(username, title, description, tags, new BuyoutStrategy(offer));
			}
			sendItemToDatabase(item);
		} catch (RemoteException e) {
			throw new RuntimeException();
		}
	}

	private void sendItemToDatabase(Item item) {
		try {
			salesManDatabaseMethods.addItemToDatabase(item);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}


}

