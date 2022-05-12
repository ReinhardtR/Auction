package server.model;

import org.junit.jupiter.api.*;
import shared.transferobjects.AuctionItem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
/*
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DatabaseIOTest {
	private DatabaseIO databaseAccess;
	private AuctionItem testItem;

	@BeforeAll
	public void createMethod(){
		databaseAccess = new DatabaseAccess();
		testItem = new AuctionItem("testeren","Description","Lang, Høj, Rødhåret, Lækkert, Smækker",800);

		try {
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager
							.getConnection("jdbc:postgresql://hattie.db.elephantsql.com:5432/isgypvka",
											"isgypvka", "UkY3C9sbYugpjto58d8FAk9M54JiLanr");


			String sql = "CREATE TABLE testTable " +
							"(itemid serial,title varchar(20), description varchar(300),tags varchar(100),currentprice numeric(8,2),currenthighestbidder varchar(20))";
			c.prepareStatement(sql).executeUpdate();


			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}



		} catch ( Exception e ) {
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
			System.exit(0);
		}


	}

	@BeforeEach
	private void addTestItem(){
		databaseAccess.addItemToAuction("testTable",testItem);
	}

	@Test
	public void connectionTest(){
			Assertions.assertDoesNotThrow(this::addTestItem);
	}

	@Test
	public void getItemTest(){
		try {
			assertEquals(databaseAccess.searchAuctionItemsFromKeyword("testeren","testTable").get(0).getTitle(),"testeren");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void removeItemTest(){

		try {
			databaseAccess.removeItemFromServer("testTable",testItem);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void incrementerTestLow() throws SQLException {
		tearDown();
		createMethod();
		for(int i = 0;i<10;i++)
		{
			databaseAccess.addItemToAuction("testTable",new AuctionItem("testeren"+i,"Description","Lang, Høj, Rødhåret, Lækkert, Smækker",800));
		}
		assertEquals(databaseAccess.searchAuctionItemsFromKeyword("testeren0","testTable").get(0).getItemId(), 1);
		assertEquals(databaseAccess.searchAuctionItemsFromKeyword("testeren9","testTable").get(0).getItemId(), 10);
	}


	@AfterEach
	public void clearTable(){
		try {
			databaseAccess.clearTable("testTable");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	@AfterAll
	void tearDown() {
		try {
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager
							.getConnection("jdbc:postgresql://hattie.db.elephantsql.com:5432/isgypvka",
											"isgypvka", "UkY3C9sbYugpjto58d8FAk9M54JiLanr");


			String sql = "DROP TABLE testTable ";
			c.prepareStatement(sql).executeUpdate();

			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} catch ( Exception e ) {
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
			System.exit(0);
		}
	}




}

 */

