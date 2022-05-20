package server.persistence;

import server.model.item.Item;
import server.model.item.ItemImpl;
import server.persistence.item.mutation.BuyingMutator;
import server.persistence.utils.SQL;
import server.persistence.utils.resultSetAdapter.ResultSetAdapter;
import server.persistence.utils.resultSetAdapter.ResultSetAdapterImpl;

import java.beans.PropertyChangeEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseAccess implements DatabaseIO {
	private final BuyingMutator buyingMutator;
	private final long ONE_HOUR_IN_MILLI;
	private final ResultSetAdapter resultSetAdapter;

	public DatabaseAccess() {
		buyingMutator = new BuyingMutator();
		ONE_HOUR_IN_MILLI = 3600000;
		resultSetAdapter = new ResultSetAdapterImpl();

		SQL.constructDatabaseTables(createConnection());
		checkAuctionTimers();
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
					buyingMutator.auctionTimers(createConnection(), this::auctionTimeIsUp);
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

	private synchronized void auctionItemBought(String itemID) {
		try {
			buyingMutator.auctionBought(createConnection(), itemID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public synchronized Item getItem(String itemID) throws SQLException {

		//itemSelector.fetchItem(createConnection(), itemID);
		return resultSetAdapter.fetchItem(createConnection(), itemID);
	}

	@Override
	public ArrayList<Item> getAmountOfItems(int amount, String ascOrDesc) throws SQLException {

		//itemSelector.fetchAmountOfItems(createConnection(), amount, ascOrDesc);
		return resultSetAdapter.fetchAmountOfItems(createConnection(), amount, ascOrDesc);
	}

	@Override
	public synchronized void buyoutItemBought(Item item) throws SQLException {
		buyingMutator.buyoutBought(createConnection(), (ItemImpl) item);
	}

	@Override
	public synchronized void updateAuctionOffer(Item item) throws SQLException {
		buyingMutator.newBid(createConnection(), (ItemImpl) item);
	}
}
