package server.softwarehouseacces;

import server.model.item.Item;
import server.model.item.ItemImpl;
import server.softwarehouseacces.item.express.ItemExpress;
import server.softwarehouseacces.item.transactions.ItemScanner;
import server.softwarehouseacces.utils.SQL;

import java.beans.PropertyChangeEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseAccess implements DatabaseIO {
	private final ItemExpress itemExpress;
	private final ItemScanner itemScanner;
	private final long ONE_HOUR_IN_MILLI;

	public DatabaseAccess() {
		itemExpress = new ItemExpress();
		itemScanner = new ItemScanner();
		ONE_HOUR_IN_MILLI = 3600000;

		//checkAuctionTimers();
	}

	private Connection createConnection() {
		Connection c = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection(
							SQL.getURL(),
							SQL.getUSERNAME(),
							SQL.getPASSWORD()
			);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		return c;
	}

	private void checkAuctionTimers() {
		new Thread(() ->
		{
			while (true) {
				try {
					itemScanner.auctionTimers(createConnection(), this::auctionTimeIsUp);
					Thread.sleep(ONE_HOUR_IN_MILLI);
				} catch (InterruptedException | SQLException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}


	private void auctionTimeIsUp(PropertyChangeEvent propertyChangeEvent) {
		System.out.println("inside auctiontimeisUp");
		auctionItemBought((String) propertyChangeEvent.getNewValue());
	}

	private void auctionItemBought(String itemID) {
		try {
			itemScanner.auctionBought(createConnection(), itemID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public synchronized Item getItem(String itemID) throws SQLException {
		return itemExpress.fetchItem(createConnection(), itemID);
	}

	@Override
	public Item[] getAmountOfItems(int amount, String ascOrDesc) throws SQLException {
		return itemExpress.fetchAmountOfItems(createConnection(),amount,ascOrDesc);
	}


	@Override
	public synchronized void buyoutItemBought(Item item) throws SQLException {
		itemScanner.buyoutBought(createConnection(), (ItemImpl) item);
	}

	@Override
	public synchronized void updateAuctionOffer(Item item) throws SQLException {
		itemScanner.newBid(createConnection(), (ItemImpl) item);
	}

	@Override
	public void clearTable(String testTable)
	{
		itemScanner.clearTable(createConnection(), testTable);
	}
}
