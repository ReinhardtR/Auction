package server.model;

import org.junit.jupiter.api.*;
import server.softwarehouseacces.DatabaseAccess;
import server.softwarehouseacces.DatabaseIO;
import server.softwarehouseacces.temps.Item;
import server.softwarehouseacces.temps.TempBuyout;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


//Her skal udfyldes gennemgående test, der er opsat skabelon for testing


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DatabaseIOTest {
	private final String AUCIONTABLENAME = "auctiontest";
	private final String BUYOUTTABLENAME = "buyouttest";
	Connection c = null;
	private DatabaseIO databaseAccess;

	@BeforeAll
	public void createMethod() {
		databaseAccess = new DatabaseAccess();

		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager
							.getConnection("jdbc:postgresql://hattie.db.elephantsql.com:5432/isgypvka",
											"isgypvka", "UkY3C9sbYugpjto58d8FAk9M54JiLanr");


			c.setAutoCommit(false);

			//Auction creator:
			String auctionTableCreator = "CREATE TABLE " + AUCIONTABLENAME +
							" AS SELECT * FROM auction" +
							" WITH NO DATA";

			c.prepareStatement(auctionTableCreator).executeUpdate();


			//Trigger creator for auction:
			//Kopieret fra det rigtige table, kan måske finde anden løsning senere
			String triggerCreator = "create trigger auctionbidchekcer" +
							"    before update" +
							"    on " + AUCIONTABLENAME +
							"    for each row" +
							"execute procedure auctionbidchecker()";

			c.prepareStatement(triggerCreator).executeUpdate();

			triggerCreator = "create trigger auctionitembought" +
							"    before delete" +
							"    on " + AUCIONTABLENAME +
							"    for each row" +
							"execute procedure auctionitembought()";


			//Buyout creator
			String buyoutTableCreator = "CREATE TABLE " + BUYOUTTABLENAME +
							" AS SELECT * FROM buyout " +
							" WITH NO DATA";

			c.prepareStatement(buyoutTableCreator).executeUpdate();

			triggerCreator = "create trigger buyoutitembought" +
							"    after update" +
							"    on " + BUYOUTTABLENAME +
							"    for each row" +
							"execute procedure buyoutitembought()";

			c.prepareStatement(triggerCreator).executeUpdate();

			c.commit();
			c.setAutoCommit(true);

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}


	//Auction table gives 5 tuples som udløber inden for en time, og 5 som udløber om mere end en time.
	public void populateAuctionTable() {
		String sql = "INSERT INTO " + AUCIONTABLENAME + " VALUES " +
						"(default,0,testName,TIMESTAMP(0) '" + LocalDateTime.now().plusMinutes(59) + "','AUCTION')";


		for (int i = 0; i < 6; i++) {
			try {
				c.prepareStatement(sql).executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		sql = "INSERT INTO " + AUCIONTABLENAME + " VALUES " +
						"(default,0,testName,TIMESTAMP(0) '" + LocalDateTime.now().plusHours(3) + "','AUCTION')";

		for (int i = 0; i < 6; i++) {
			try {
				c.prepareStatement(sql).executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


	@Test
	@DisplayName("Get Item fra database tester")
	void getItemTester() throws SQLException {
		assertEquals(1, databaseAccess.getItem(1).getId(), "Get an item we have set");
		assertThrows(SQLException.class, () -> {
			databaseAccess.getItem(300);
		}, "Try to get an item that hasn't been set");
	}


	@Test
	void buyBuyoutItemTester() throws SQLException {
		databaseAccess.buyoutItemBought(new Item(6, new TempBuyout(0, "testName", "BUYOUT")));

		assertEquals(6, databaseAccess.getItem(6).getId());
	}

	@Test
	void updateItemOfferTester() {


	}

	@Test
	void clearTableTester() {

	}


	protected void populateBuyoutTable() {
		String sql = "INSERT INTO " + BUYOUTTABLENAME + " VALUES " +
						"(default,0,testName,'BUYOUT')";


		for (int i = 0; i < 6; i++) {
			try {
				c.prepareStatement(sql).executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


	protected void clearBuyoutTable() {
		try {
			databaseAccess.clearTable(BUYOUTTABLENAME);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void clearAuctionTable() {
		try {
			databaseAccess.clearTable(AUCIONTABLENAME);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	@AfterAll
	void tearDown() {
		try {
			String sql = "DROP TABLE testTable ";
			c.prepareStatement(sql).executeUpdate();

			try {
				c.close(); // Dette er det eneste sted hvor vi lukker connection
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}


}

