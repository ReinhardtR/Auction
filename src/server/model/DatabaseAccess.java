package server.model;

import server.model.auction.Item;
import server.model.temps.TempAuction;
import server.model.temps.TempItem;
import shared.transferobjects.AuctionItem;

import java.sql.*;
import java.util.ArrayList;

/*
Der skal huskes at synchronize på metoderne så man ikke kommer til at lave fejl med flere brugere
Herudover skal der laves nogle transaction så vores "flere metodet" metoder som henter fra databasen i flere omgange.
 */


public class DatabaseAccess implements DatabaseIO {


	private Connection c = null;
	private PreparedStatement pstmt = null;

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

/*
	@Override
	public void addItemToAuction(String relation, TempItem item) {
		createConnection();
		if (relation.equalsIgnoreCase("buyout"))
		{
			try {

				String sql = "INSERT INTO \"public\"." + relation + "(itemID,price,buyer,salestrategy) + VALUES(?,?,?,?)";
				pstmt = c.prepareStatement(sql);

				pstmt.setString(1,"default");
				pstmt.setString(2, String.valueOf(item.getTempSaleStrategy().getOffer()));
				pstmt.setString(3,item.getTempSaleStrategy().getUsernameFromBuyer());
				pstmt.setString(4,"BUYOUT");

				pstmt.executeQuery();

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		else if(relation.equalsIgnoreCase("auction"))
		{

		try {

			String sql = "INSERT INTO \"public\"." + relation + "(itemid,currentbid,currentbidder,auctionenddate,salestrategy)" + "VALUES(?,?,?,?,?)";

			pstmt = c.prepareStatement(sql);

			pstmt.setString(1,"default");
			pstmt.setString(2, String.valueOf(item.getTempSaleStrategy().getOffer()));
			pstmt.setString(3,item.getTempSaleStrategy().getUsernameFromBuyer());
			pstmt.setDouble(4,(TempAuction) item.getTempSaleStrategy().getEndDate());
			pstmt.setString(5,"reinhardt"); //Person som har sat salget op.

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
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
		try {		
			String sql = "SELECT * FROM \"public\".Item WHERE itemID = " + itemID;

			pstmt = c.prepareStatement(sql);
			
			resultSet = pstmt.executeQuery();


		}
		
		catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			resultSet.next();
			if (resultSet.getString("saleStrategy").equalsIgnoreCase("auction"))
			{
       //Hvis dataene modtaget er auction, laves der et auction item som gives til user
			}
			else if (resultSet.getString("saleStrategy").equalsIgnoreCase("buyout"))
			{
				//Hvis dataene modtaget er buyout, laves der et buyout item som gives til user

			}
			else
			{
				//intet
			}



			closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; //Returnere det item som blev returneret af databasen.
	}



	@Override
	public void buyoutItemBought(TempItem item) {
		createConnection();

		String sql = "UPDATE \"public\".buyout SET buyer = " + item.getTempSaleStrategy().getUsernameFromBuyer() + "WHERE itemID = " + item.getId();

		try {
			pstmt = c.prepareStatement(sql);
			pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		sql = "REMOVE FROM \"public\".buyout WHERE itemID = " + item; //Ændres når "køb" use casen er færdig

		try {
			pstmt = c.prepareStatement(sql);
			pstmt.executeQuery();


		} catch (SQLException e) {
			e.printStackTrace();
		}

		closeConnection();

		//The item has been "bought" (removed from the table)
		//Efter item er blevet fjernet skal der muligvist kaldes en metode eller returneres noget som kan
		// vise køberen at item'et er blevet købt.
	}


	@Override
	public void updateItemOffer(TempItem item) {
	createConnection();

	String sql = "UPDATE \"public\".auction SET currentbid =" + item.getTempSaleStrategy().getOffer();

		try {
			pstmt = c.prepareStatement(sql);
			pstmt.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeConnection();
	}




	public static void main(String[] args) {

		DatabaseAccess  databaseAccess = new DatabaseAccess();

		databaseAccess.getItem(1);
		databaseAccess.getItem(2);

	}


}


