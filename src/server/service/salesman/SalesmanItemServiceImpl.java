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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class SalesmanItemServiceImpl implements SalesmanItemService {
	private final SalesmanDatabaseMethods salesmanDatabaseMethods;

	public SalesmanItemServiceImpl() {
		salesmanDatabaseMethods = new DatabaseAccess();
	}

	@Override
	public void createAndSendItemToDB(String title, String description, String tags, SaleStrategyType saleType, String username, double offer, LocalTime endTime, LocalDate endDate) {
		System.out.println(endDate.toString());
		Item item = constructItem(title, description, tags, saleType, username, offer, endTime, endDate);
		sendItemToDatabase(item);
	}

	private Item constructItem(String title, String description, String tags, SaleStrategyType saleType, String username, double offer, LocalTime endTime, LocalDate endDate) {
		Item item = null;

		try {
			System.out.println(LocalDateTime.of(endDate, endTime));
			if (saleType == SaleStrategyType.AUCTION) {
				item = new ItemImpl(null, username, title, description, tags, new AuctionStrategy(offer, null, LocalDateTime.of(endDate, endTime)));
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

