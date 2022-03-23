package server.model;

import shared.transferobjects.AuctionItem;

import java.sql.*;

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

	private int itemID = 0;
	private Connection c = null;
	private PreparedStatement pstmt = null;

	public static void main(String[] args) {
		DatabaseAccess d = new DatabaseAccess();

		d.addItemToAuction(new AuctionItem("test2","test2Desc","Lang,Tismand,Stor,Lang,Sort",4200));
		// Ovenstående metode skal kaldes når et item skal til salg og oprettes på databasen.
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
			pstmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void addItemToAuction(AuctionItem item) {
		createConnection();

		try {
			String sql = "INSERT INTO \"AuctionData\".auctionitems(itemid,title,description,tags,currentprice,currenthighestbidder)" + "VALUES(?,?,?,?,?,?)";

			pstmt = c.prepareStatement(sql);

			pstmt.setString(1,"" + itemIDIncrementer());
			pstmt.setString(2,item.getTitle());
			pstmt.setString(3,item.getDescription());
			pstmt.setString(4,item.getTags());
			pstmt.setDouble(5,item.getStarterPrice()); //Starter pris for item
			pstmt.setString(6,"reinhardt"); //Person som har sat salget op.

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		closeConnection();
	}

	@Override
	public void removeItemFromServer(AuctionItem item) {
		createConnection();

		//Insert here

		closeConnection();
	}

	private int itemIDIncrementer()
	{
		itemID++;
		return itemID;
	}






}
