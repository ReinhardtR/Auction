package server.model;

import shared.transferobjects.AuctionItem;

import java.sql.*;
import java.util.ArrayList;

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

	private int itemID = 1;
	private Connection c = null;
	private PreparedStatement pstmt = null;

	public static void main(String[] args) throws InterruptedException, SQLException {
		DatabaseAccess d = new DatabaseAccess();
		AuctionItem auctionItem = new AuctionItem("test2","test2Desc","Lang,Tismand,Stor,Lang,Sort",4200);
		d.addItemToAuction(auctionItem);
		// Ovenstående metode skal kaldes når et item skal til salg og oprettes på databasen.



			Thread.sleep(10000);

		d.searchAuctionItemsFromKeyword("test");
	}

	// TODO: 23/03/2022 Der KAN ske en fejl her hvis flere clienter laver en connection uden det bliver closed.
	private void createConnection() {
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager
							.getConnection("jdbc:postgresql://hattie.db.elephantsql.com:5432/isgypvka",
											"isgypvka", "1234");




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

			String sql = "INSERT INTO \"public\".auctionitems(itemid,title,description,tags,currentprice,currenthighestbidder)" + "VALUES(?,?,?,?,?,?)";

			pstmt = c.prepareStatement(sql);

			pstmt.setString(1,"" + getItemID());
			pstmt.setString(2,item.getTitle());
			pstmt.setString(3,item.getDescription());
			pstmt.setString(4,item.getTags());
			pstmt.setDouble(5,item.getPrice()); //Starter pris for item
			pstmt.setString(6,"reinhardt"); //Person som har sat salget op.

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		itemIDIncrementer();
		closeConnection();
	}

	private int getItemID() {
	return itemID;
	}

	@Override
	public void removeItemFromServer(AuctionItem item) throws SQLException {
		createConnection();

		String sql = "DELETE FROM \"public\".auctionitems WHERE title='"+item.getItemId()+"'";
		c.prepareStatement(sql).executeUpdate();

		closeConnection();
	}

	@Override
	public ArrayList<AuctionItem> searchAuctionItemsFromKeyword(String keyword) throws SQLException {

		createConnection();
		ArrayList<AuctionItem> listOfItems = new ArrayList<>();
		Statement stmnt = c.createStatement();


		ResultSet resultSet = stmnt.executeQuery("SELECT * FROM \"public\".auctionitems WHERE title like '%" + keyword + "%'");

		while(resultSet.next())
		{
			String title = resultSet.getString("title");
			String description = resultSet.getString("description");
			String tags = resultSet.getString("tags");
			double currentPrice = resultSet.getDouble("currentprice");
			System.out.println("IN A LOOOOOOP");
			listOfItems.add(new AuctionItem(title,description,tags,currentPrice));
		}
		System.out.println("out of loop");
		System.out.println(listOfItems.toString());

		closeConnection();
		
		return listOfItems;

	}


	private void itemIDIncrementer()
	{
		itemID++;
	}






}
