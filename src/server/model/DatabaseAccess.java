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

	private int itemID;
	private Connection c = null;
	private PreparedStatement pstmt = null;

	public DatabaseAccess() throws SQLException {
		//getLatestId(relation);
	}

	private void createConnection() {
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager
							.getConnection("jdbc:postgresql://hattie.db.elephantsql.com:5432/isgypvka",
											"isgypvka", "UkY3C9sbYugpjto58d8FAk9M54JiLanr");




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
	public void addItemToAuction(String relation, AuctionItem item) {
		createConnection();

		try {

			String sql = "INSERT INTO \"public\"." + relation + "(itemid,title,description,tags,currentprice,currenthighestbidder)" + "VALUES(?,?,?,?,?,?)";

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

		String sql = "DELETE FROM \"public\".auctionitems WHERE title='"+item.getTitle()+"'";
		int homie = c.prepareStatement(sql).executeUpdate();
		System.out.println(homie);
		closeConnection();
	}

	@Override
	public ArrayList<AuctionItem> searchAuctionItemsFromKeyword(String keyword, String relation) throws SQLException {

		createConnection();

		ArrayList<AuctionItem> listOfItems = new ArrayList<>();
		Statement stmnt = c.createStatement();


		ResultSet resultSet = stmnt.executeQuery("SELECT * FROM \"public\"."+relation+" WHERE title like '%" + keyword + "%'");

		while(resultSet.next())
		{
			String title = resultSet.getString("title");
			String description = resultSet.getString("description");
			String tags = resultSet.getString("tags");
			double currentPrice = resultSet.getDouble("currentprice");
			listOfItems.add(new AuctionItem(title,description,tags,currentPrice));
		}

		closeConnection();

		return listOfItems;

	}


	public void updateHighestBidder(AuctionItem item) throws SQLException {
		createConnection();

		String currenthighestbidder = "UPDATE \"public\".auctionitems SET currenthighestbidder ='xxMilosLongSchlongxx'";
		c.prepareStatement(currenthighestbidder).executeUpdate();
		String currentprice = "UPDATE \"public\".auctionitems SET currentprice ='6969.69'";
		c.prepareStatement(currentprice).executeUpdate();


		closeConnection();
	}

	public void clearTable(String relation) throws SQLException {
		createConnection();


		String sql = "TRUNCATE TABLE \"public\"." + relation;

		c.prepareStatement(sql).executeUpdate();


		closeConnection();
	}

	@Override
	public int getLatestId(String relation) throws SQLException {
		createConnection();
		Statement stmnt = c.createStatement();
		ResultSet resultSet = stmnt.executeQuery("SELECT * FROM \"public\"."+relation);

		int latestIncrement;

		if(resultSet.next())
		{
			latestIncrement = resultSet.getInt("latestItemId");
		}
		else
		{
			throw new SQLException("Latest itemId does not exist");
		}

		closeConnection();

		return latestIncrement;
	}

	private void itemIDIncrementer()
	{
		itemID++;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
}
