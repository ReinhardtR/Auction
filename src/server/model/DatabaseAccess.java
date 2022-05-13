package server.model;

import server.model.auction.Item;
import server.model.temps.TempAuction;
import server.model.temps.TempBuyout;
import server.model.temps.TempItem;
import shared.transferobjects.AuctionItem;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

/*
Der skal huskes at synchronize på metoderne så man ikke kommer til at lave fejl med flere brugere
Herudover skal der laves nogle transaction så vores "flere metodet" metoder som henter fra databasen i flere omgange.
 */


public class DatabaseAccess implements DatabaseIO {


	private Connection c = null;                  //Flyttes
	private PreparedStatement pstmt = null;       //Flyttes





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

	@Override
	public synchronized Item getItem(int itemID) {
		createConnection();

		ResultSet resultSet = null;
		String selecter = "SELECT itemID, saleStrategy FROM \"public\".Auction WHERE itemID = " + itemID;
		String selecter2 = "SELECT itemID, saleStrategy FROM \"public\".Buyout WHERE itemID = " + itemID;
		try {
			String sql = selecter +
							" UNION " +
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
				return null; //Skal senere returnere item, har temp ligenu
			} else if (resultSet.getString("saleStrategy").equalsIgnoreCase("buyout")) {
				String buyoutItemGetterSQL = "SELECT * FROM \"public\".Buyout " +
								" WHERE itemID = " + itemID;
				pstmt = c.prepareStatement(buyoutItemGetterSQL);
				ResultSet buyoutResult = pstmt.executeQuery();
				buyoutResult.next();
				TempItem buyout = buyoutTransport(buyoutResult);
				closeConnection();
				System.out.println(buyout);
				return null; //Skal senere returnere item, har temp ligenu
			} else {
				//intet
			}


			closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; //Returnere det item som blev returneret af databasen.
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
						auctionResult.getTimestamp("AuctionEndDate").toLocalDateTime(),
						auctionResult.getString("saleStrategy"));
		return new TempItem(auctionResult.getInt("itemID"), tempAuction);
	}

	@Override
	public synchronized void buyoutItemBought(TempItem item) {
		createConnection();

		String sql = "UPDATE \"public\".buyout SET buyer = '" + item.getTempSaleStrategy().getUsernameFromBuyer() + "' WHERE itemID = " + item.getId();

		try {
			pstmt = c.prepareStatement(sql);
			pstmt.execute();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		closeConnection();

		//The item has been "bought" (removed from the table)
		//Efter item er blevet fjernet skal der muligvist kaldes en metode eller returneres noget som kan
		// vise køberen at item'et er blevet købt.
	}


	@Override
	public synchronized void updateItemOffer(TempItem item) {
		createConnection();

		String sql = "UPDATE \"public\".auction SET currentBid = " + item.getTempSaleStrategy().getOffer() +
						", currentBidder = '" + item.getTempSaleStrategy().getUsernameFromBuyer() + "'";

		try {
			pstmt = c.prepareStatement(sql);
			pstmt.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeConnection();
	}

	@Override
	public void clearTable(String tableToClear)  {
		String sql = "TRUNCATE " + tableToClear;

		try {
			c.prepareStatement(sql).executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void checkAuctionTimers() {
		new Thread(() ->
		{
			while (true) {
				createConnection();
				ResultSet resultSet = null;

				LocalDateTime localTimeIn1Hour = LocalDateTime.now().plusHours(1);
				String checkTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(localTimeIn1Hour);
				String selecter = "SELECT itemID, auctionenddate FROM \"public\".Auction " +
								"WHERE auctionenddate < " + checkTime +
								" ORDER BY auctionenddate";
				try {

					String sql = selecter;

					pstmt = c.prepareStatement(sql);
					resultSet = pstmt.executeQuery();
					closeConnection();


					while (resultSet.next()) {
						int tempItemID = resultSet.getInt("itemID");
						Timestamp tempEndTime = resultSet.getTimestamp("AuctionEndDate");
						new Thread(new AuctionCountDown(tempItemID, tempEndTime, localTimeIn1Hour, this::auctionTimeIsUp)).start();
					}
					closeConnection();

				} catch (SQLException throwables) {
					throwables.printStackTrace();
				}
				try {
					Thread.sleep(60 * 60 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void auctionTimeIsUp(PropertyChangeEvent propertyChangeEvent) {
		auctionItemBought((int) propertyChangeEvent.getNewValue());
	}

	private void auctionItemBought(int itemID) {
		System.out.println("Ready to delete: " + itemID);                   //Skal fjernes senere
		createConnection();
		String sql = "DELETE \"public\".auction WHERE itemID = " + itemID;

		try {

			pstmt = c.prepareStatement(sql);
			pstmt.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeConnection();
	}

	private class AuctionCountDown implements Runnable {
		private int itemID;
		private Timestamp endTime;
		private LocalDateTime localTimeIn1Hour;
		private PropertyChangeSupport support;

		public AuctionCountDown(int itemID, Timestamp endTime, LocalDateTime localTimeIn1Hour, PropertyChangeListener listener) {
			this.itemID = itemID;
			this.endTime = endTime;
			this.localTimeIn1Hour = localTimeIn1Hour;
			support = new PropertyChangeSupport(this);
			support.addPropertyChangeListener(listener);
		}


		//closeConnection();

	//	return listOfItems;



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


/*
	@Override
	public int getLatestId(String relation) throws SQLException {
		createConnection();
		Statement stmnt = c.createStatement();
		ResultSet resultSet = stmnt.executeQuery("SELECT * FROM \"public\"."+relation);

		int latestIncrement;

		if(resultSet.next())
		{
			latestIncrement = resultSet.getInt("latestItemId");
*/
		@Override
		public void run() {

			Duration duration = Duration.between(localTimeIn1Hour, (Temporal) endTime);
			System.out.println("itemID = " + itemID + " | " + "endtime = " + endTime);             //Skal fjernes senere
			try {
				Thread.sleep(duration.toMillis());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			support.firePropertyChange("Time is up on item " + itemID, null, itemID);

	}

	}


}
