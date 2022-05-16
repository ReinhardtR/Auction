package server.softwarehouseacces;

import server.softwarehouseacces.item.express.ItemExpress;
import server.softwarehouseacces.item.transactions.ItemScanner;
import server.softwarehouseacces.temps.Item;

import java.beans.PropertyChangeEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseAccess implements DatabaseIO {
	private final ItemExpress itemExpress;
	private final ItemScanner itemScanner;

	public DatabaseAccess() {
		itemExpress = new ItemExpress();
		itemScanner = new ItemScanner();

		//checkAuctionTimers();
	}

	private Connection createConnection() {
		Connection c = null;
		try {
			Class.forName("org.postgresql.Driver");

			c = DriverManager.getConnection(
							"jdbc:postgresql://hattie.db.elephantsql.com:5432/isgypvka",
							"isgypvka",
							"UkY3C9sbYugpjto58d8FAk9M54JiLanr"
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
					Thread.sleep(60 * 60 * 1000);
				} catch (InterruptedException | SQLException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void auctionTimeIsUp(PropertyChangeEvent propertyChangeEvent) {
		System.out.println("inside auctiontimeisUp");
		auctionItemBought((int) propertyChangeEvent.getNewValue());
	}

	private void auctionItemBought(int itemID) {
		try {
			itemScanner.auctionBought(createConnection(), itemID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public synchronized Item getItem(int itemID) throws SQLException {
		return itemExpress.fetchItem(createConnection(), itemID);
	}


	@Override
	public synchronized void buyoutItemBought(Item item) throws SQLException {
		itemScanner.buyoutBought(createConnection(), item);
	}

	@Override
	public synchronized void updateAuctionOffer(Item item) throws SQLException {
		itemScanner.newBid(createConnection(), item);
	}

	@Override
	public void clearTable(String testTable) throws SQLException {
		itemScanner.clearTable(createConnection(),testTable);
	}
}
