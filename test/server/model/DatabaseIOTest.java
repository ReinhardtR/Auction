
package server.model;

import org.junit.jupiter.api.*;
import org.junit.runners.Parameterized;
import shared.transferobjects.AuctionItem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DatabaseIOTest {
	private DatabaseIO databaseAccess;
	private final String AUCIONTABLENAME = "auctiontest";
	private final String BUYOUTTABLENAME = "buyouttest";
	Connection c = null;
	@BeforeAll
	public void createMethod(){
		databaseAccess = new DatabaseAccess();

		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager
							.getConnection("jdbc:postgresql://hattie.db.elephantsql.com:5432/isgypvka",
											"isgypvka", "UkY3C9sbYugpjto58d8FAk9M54JiLanr");


			//Auction creator:
			String auctionTableCreator = "CREATE TABLE " + AUCIONTABLENAME +
							" AS SELECT * FROM auction" +
							" WITH NO DATA";

			c.prepareStatement(auctionTableCreator).executeUpdate();


			//Trigger creator for auction:
			//Kopieret fra det rigtige table, kan måske finde anden løsning senere
			String triggerCreator = "create trigger auctionbidchekcer" +
							"    before update" +
							"    on "+ AUCIONTABLENAME +
							"    for each row" +
							"execute procedure auctionbidchecker()";

			c.prepareStatement(triggerCreator).executeUpdate();

			triggerCreator = "create trigger auctionitembought" +
							"    before delete" +
							"    on " +AUCIONTABLENAME +
							"    for each row" +
							"execute procedure auctionitembought()";



			//Buyout creator
			String buyoutTableCreator = "CREATE TABLE " + BUYOUTTABLENAME +
							" AS SELECT * FROM buyout " +
							" WITH NO DATA";


			triggerCreator = "create trigger buyoutitembought" +
							"    after update" +
							"    on " + BUYOUTTABLENAME +
							"    for each row" +
							"execute procedure buyoutitembought()";

			c.prepareStatement(buyoutTableCreator).executeUpdate();

				//Lav en samlet transaction med alle disse metodekald,
			// så det bliver gjort på én gang. Altså setautocommit til false og kald en commit til sidst
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
			System.exit(0);
		}
	}



//Auction table gives 5 tuples som udløber inden for en time, og 5 som udløber om mere end en time.
	public void populateAuctionTable() {
		String sql = "INSERT INTO "+AUCIONTABLENAME+" VALUES " +
						"(default,0,null,TIMESTAMP(0) '"+ LocalDateTime.now().plusMinutes(59) +"','AUCTION')";


		for (int i = 0; i < 6; i++) {
			try {
				c.prepareStatement(sql).executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		sql = "INSERT INTO "+AUCIONTABLENAME+" VALUES " +
						"(default,0,null,TIMESTAMP(0) '"+ LocalDateTime.now().plusHours(3) +"','AUCTION')";

		for (int i = 0; i < 6; i++) {
			try {
				c.prepareStatement(sql).executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


	protected void populateBuyoutTable(){
		String sql = "INSERT INTO "+ BUYOUTTABLENAME+" VALUES " +
						"(default,0,null,'BUYOUT')";


		for (int i = 0; i < 6; i++) {
			try {
				c.prepareStatement(sql).executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


	protected void clearBuyoutTable(){
		try {
			databaseAccess.clearTable(BUYOUTTABLENAME);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void clearAuctionTable(){
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

		} catch ( Exception e ) {
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
			System.exit(0);
		}
	}




}

