package server.persistence;

import server.model.item.ItemImpl;
import server.persistence.item.adderToDatabase.AdderToDatabase;
import server.persistence.item.mutation.BuyingMutator;
import server.persistence.utils.SQL;
import server.persistence.utils.resultSetAdapter.ResultSetAdapter;
import server.persistence.utils.resultSetAdapter.ResultSetAdapterImpl;
import shared.SaleStrategyType;
import shared.network.model.Item;

import java.beans.PropertyChangeEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class DatabaseAccess implements CustomerDatabaseMethods, SalesManDatabaseMethods {
	private final BuyingMutator buyingMutator;
	private final long ONE_HOUR_IN_MILLI;
	private final ResultSetAdapter resultSetAdapter;
	private final AdderToDatabase adderToDatabase;


	public DatabaseAccess() {
		buyingMutator = new BuyingMutator();
		ONE_HOUR_IN_MILLI = 3600000;
		resultSetAdapter = new ResultSetAdapterImpl();
		adderToDatabase = new AdderToDatabase();
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

	private void auctionItemBought(String itemID) {
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
	public List<Item> getAmountOfItems(int amount, String ascOrDesc) throws SQLException {

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

	@Override
	public void addItemToDatabase(Item itemToAdd) throws SQLException {
		adderToDatabase.addItemToDatabase(createConnection(),(ItemImpl) itemToAdd);
	}

	@Override
	public void AlterItemOnDatabsae(String itemIDToAlter, String columnToAlter, String newValue) throws SQLException {

	}


	public static void main(String[] args) {


		DatabaseAccess access = new DatabaseAccess();
		SQL.constructDatabaseTables(access.createConnection());
		SQL.addAuctionItem(0, LocalDateTime.now(), SaleStrategyType.AUCTION);
	}
}
