package server.service.salesman;

import server.model.item.ItemImpl;
import server.model.item.sale_strategy.AuctionStrategy;
import server.model.item.sale_strategy.BuyoutStrategy;
import server.persistence.DatabaseAccess;
import server.persistence.SalesmanDatabaseMethods;
import shared.SaleStrategyType;
import shared.network.model.Item;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class SalesmanItemServiceImpl implements SalesmanItemService {
	private final SalesmanDatabaseMethods salesmanDatabaseMethods;

	public SalesmanItemServiceImpl() {
		salesmanDatabaseMethods = new DatabaseAccess();
	}

	@Override
	public void createAndSendItemToDB(String title, String description, String tags, SaleStrategyType saleType, String username, double offer, String endtime) {
		Item item = constructItem(title, description, tags, saleType, username, offer, endtime);
		sendItemToDatabase(item);
	}

	private Item constructItem(String title, String description, String tags, SaleStrategyType saleType, String username, double offer, String endtime) {
		Item item = null;

		try {
			if (saleType == SaleStrategyType.AUCTION) {
				item = new ItemImpl(null, username, title, description, tags, new AuctionStrategy(offer, null, LocalDateTime.of(2022, 5, 26, 18, 0, 0)));
			} else if (saleType == SaleStrategyType.BUYOUT) {
				item = new ItemImpl(null, username, title, description, tags, new BuyoutStrategy(offer));
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return item;
	}

	private void sendItemToDatabase(Item item) {
		try {
			salesmanDatabaseMethods.addItemToDatabase(item);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}

