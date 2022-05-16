package server.model;

import org.junit.jupiter.api.*;
import server.softwarehouseacces.DatabaseAccess;
import server.softwarehouseacces.DatabaseIO;
import server.softwarehouseacces.temps.Item;
import server.softwarehouseacces.temps.TempAuction;
import server.softwarehouseacces.temps.TempBuyout;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


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

		//	c.setAutoCommit(false);

			//Auction creator:
			String auctionTableCreator = "CREATE TABLE " + AUCIONTABLENAME +
							" AS SELECT * FROM auction" +
							" WITH NO DATA";

			c.prepareStatement(auctionTableCreator).executeUpdate();
			System.out.println("CREATED AUCTION!!");

			//Trigger creator for auction:
			//Kopieret fra det rigtige table, kan måske finde anden løsning senere
			String triggerCreator = "create trigger auctionbidchekcer" +
							" before update" +
							" on " + AUCIONTABLENAME +
							" for each row" +
							" execute procedure auctionbidchecker()";

			c.prepareStatement(triggerCreator).executeUpdate();
			System.out.println("auction trigger 1 at the ready");
			triggerCreator = "create trigger auctionitembought" +
							"    before delete" +
							"    on " + AUCIONTABLENAME +
							"    for each row " +
							"execute procedure auctionitembought()";

			c.prepareStatement(triggerCreator).executeUpdate();
			System.out.println("auction trigger 2 at the ready");


			//Buyout creator
			String buyoutTableCreator = "CREATE TABLE " + BUYOUTTABLENAME +
							" AS SELECT * FROM buyout " +
							" WITH NO DATA";

			c.prepareStatement(buyoutTableCreator).executeUpdate();

			System.out.println("Tistrold");
			triggerCreator = "create trigger buyoutitembought" +
							"    after update" +
							"    on " + BUYOUTTABLENAME +
							"    for each row " +
							"execute procedure buyoutitembought()";

			c.prepareStatement(triggerCreator).executeUpdate();

		//	c.commit();
			//c.setAutoCommit(true);
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}





	@Test
	@DisplayName("Get Item fra database tester")
	void getItemTester() throws SQLException {
		populateAuctionTable();
		populateBuyoutTable();

		//get item from auction table
		assertEquals("AUCTION", databaseAccess.getItem(1).getTempSaleStrategy().getSalesMethod(), "Get an item we have set");
		assertThrows(SQLException.class, () -> {
			databaseAccess.getItem(300);
		}, "Try to get an item that hasn't been set");


		//get item from buyout table
		assertEquals("BUYOUT",databaseAccess.getItem(6).getTempSaleStrategy().getSalesMethod());
		assertThrows(SQLException.class, () -> {
			databaseAccess.getItem(300);
		}, "Try to get an item that hasn't been set");

	}


	@Test
	void buyBuyoutItemTester() throws SQLException {
populateBuyoutTable();
		databaseAccess.buyoutItemBought(new Item(6, new TempBuyout(0, "testName", "BUYOUT")));

		assertEquals(6, databaseAccess.getItem(6).getId());
clearBuyoutTable();
	}

	@Test
	void updateItemOfferTester() {
		populateAuctionTable();
		try {
			//Currentbid burde være 0
			assertEquals(0,databaseAccess.getItem(1).getTempSaleStrategy().getOffer());


			databaseAccess.updateAuctionOffer(new Item(1,new TempAuction(100,"testman2",null,"AUCTION")));

			//Nu den 100
			assertEquals(100,databaseAccess.getItem(1).getTempSaleStrategy().getOffer());

			//Alt andet er stadig 0
			for (int i = 2; i < 6; i++) {
				assertEquals(0,databaseAccess.getItem(i).getTempSaleStrategy().getOffer());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

clearAuctionTable();
	}

	@Test
	void clearTableTester() {
		try {
			databaseAccess.clearTable(AUCIONTABLENAME);
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
			String sql = "DROP TABLE testTable";
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

