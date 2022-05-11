package server.model;

import server.model.auction.Item;
import server.model.temps.TempAuction;
import server.model.temps.TempBuyout;
import server.model.temps.TempItem;

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
	private Connection c = null;
	private PreparedStatement pstmt = null;

	public static void main(String[] args) {

		DatabaseAccess databaseAccess = new DatabaseAccess();

		databaseAccess.getItem(1);
		//databaseAccess.getItem(2);

	}

	private void createConnection() {
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager
							.getConnection("jdbc:postgresql://hattie.db.elephantsql.com:5432/isgypvka",
											"isgypvka", "UkY3C9sbYugpjto58d8FAk9M54JiLanr");


		} catch (Exception e) {
			System.out.println("Ends here");
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
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

	/*
	@Override
	public void addItemToAuction(String relation, AuctionItem item) {
		createConnection();

		try {

			String sql = "INSERT INTO \"public\"." + relation + "(title,description,tags,currentprice,currenthighestbidder)" + "VALUES(?,?,?,?,?)";

			pstmt = c.prepareStatement(sql);

			pstmt.setString(1,item.getTitle());
			pstmt.setString(2,item.getDescription());
			pstmt.setString(3,item.getTags());
			pstmt.setDouble(4,item.getPrice()); //Starter pris for item
			pstmt.setString(5,"reinhardt"); //Person som har sat salget op.

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeConnection();

	}

	@Override
	public void removeItemFromServer(String relation , AuctionItem item) throws SQLException {
		createConnection();

		String sql = "DELETE FROM \"public\".auctionitems WHERE title='"+item.getTitle()+"'"; //Kan snildt ændres
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
			int itemId = resultSet.getInt("itemid");
			String title = resultSet.getString("title");
			String description = resultSet.getString("description");
			String tags = resultSet.getString("tags");
			double currentPrice = resultSet.getDouble("currentprice");

			AuctionItem auctionItem = new AuctionItem(title,description,tags,currentPrice);
			auctionItem.setItemId(itemId);
			listOfItems.add(auctionItem);
		}

		closeConnection();

		return listOfItems;

	}

	public void updateHighestBidder(AuctionItem item) throws SQLException {
		createConnection();

		String currenthighestbidder = "UPDATE \"public\".auctionitems SET currenthighestbidder ='xxMilosLongSchlongxx'";
		c.prepareStatement(currenthighestbidder).executeUpdate();
		String currentprice = "UPDATE \"public\".auctionitems SET currentprice ='6969.69'"; //Ændres til item price.
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

*/
	@Override
	public Item getItem(int itemID) {
		createConnection();
		ResultSet resultSet = null;
		String selecter = "SELECT itemID, saleStrategy FROM \"public\".Auction WHERE itemID = " + itemID;
		String selecter2 = "SELECT itemID, saleStrategy FROM \"public\".Buyout WHERE itemID = " + itemID;
		try {

			String sql = selecter +
							"UNION" +
							selecter2;

			pstmt = c.prepareStatement(sql);
			resultSet = pstmt.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			assert resultSet != null;
			resultSet.next();
			if (resultSet.getString("saleStrategy").equalsIgnoreCase("auction")) {
				String auctionItemGetterSQL = "SELECT * FROM \"public\".Auction " +
								" WHERE itemID = " + itemID;
				pstmt = c.prepareStatement(auctionItemGetterSQL);
				ResultSet auctionResult = pstmt.executeQuery();
				auctionResult.next();
				TempItem auction = auctionTransport(auctionResult);
				closeConnection();
				System.out.println(auction);
				return null;
			} else if (resultSet.getString("saleStrategy").equalsIgnoreCase("buyout")) {
				String buyoutItemGetterSQL = "SELECT * FROM \"public\".Buyout " +
								" WHERE itemID = " + itemID;
				pstmt = c.prepareStatement(buyoutItemGetterSQL);
				ResultSet buyoutResult = pstmt.executeQuery();
				buyoutResult.next();
				TempItem buyout = buyoutTransport(buyoutResult);
				closeConnection();
				System.out.println(buyout);
				return null;
			} else {
				//intet
			}


			closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private TempItem buyoutTransport(ResultSet buyoutResult) throws SQLException {
		TempBuyout tempBuyout = new TempBuyout(Double.parseDouble(buyoutResult.getString("price")),
						buyoutResult.getString("buyer"),
						buyoutResult.getString("saleStrategy"));
		TempItem tempItem = new TempItem(buyoutResult.getInt("itemID"), tempBuyout);
		return tempItem;
	}

	private TempItem auctionTransport(ResultSet auctionResult) throws SQLException {
		TempAuction tempAuction = new TempAuction(Double.parseDouble(auctionResult.getString("currentBid")),
						auctionResult.getString("currentBidder"),
						auctionResult.getDate("AuctionEndDate"),
						auctionResult.getString("saleStrategy"));
		TempItem tempItem = new TempItem(auctionResult.getInt("itemID"), tempAuction);
		return tempItem;
	}

	;

	@Override
	public void itemBought(int itemID) {

	}

	@Override
	public void updateItemOffer(Item item) {

	}


}


