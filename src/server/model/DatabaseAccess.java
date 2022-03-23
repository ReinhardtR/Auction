package server.model;

import shared.transferobjects.Item;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseAccess implements DatabaseIO {
	/* Table omkring items på database hedder: AuctionItems.

	Kolonner på table (For Auction Item):
	- Item ID PK
	- Titel
	- Beskrivelse
	- Tags
	- Currentprice
	- CurrentHighestBidder
	 */


	private Connection c = null;
	private Statement stmt = null;

	public static void main(String[] args) {
		DatabaseAccess d = new DatabaseAccess();
		d.addItemToAuction(new Item());
	}

	// TODO: 23/03/2022 Der KAN ske en fejl her hvis flere clienter laver en connection uden det bliver closed.
	private void createConnection() {
		try {
			Class.forName("org.postgresql.Driver");
			System.out.println("Hellooooo");
			c = DriverManager
							.getConnection("jdbc:postgresql://localhost:5432/postgres",
											"postgres", "6456");

			System.out.println("Database connection is up");



		} catch ( Exception e ) {
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
			System.exit(0);
		}
	}

	private void closeConnection() {

		try {
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void addItemToAuction(Item item) {
		createConnection();

		try {
			stmt = c.createStatement();
			String sql = "insert into \"AuctionData\".auctionitems(itemid,title,description,tags,currentprice,currenthighestbidder)" + "values(12345678,titleTest,Dette er en deskription, Tismand og tiskon, 69420,homy)";
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		closeConnection();
	}

	@Override
	public void removeItemFromServer(Item item) {
		createConnection();

		//Insert here

		closeConnection();
	}






}
