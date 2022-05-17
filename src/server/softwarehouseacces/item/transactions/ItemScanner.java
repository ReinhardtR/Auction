package server.softwarehouseacces.item.transactions;


import server.softwarehouseacces.item.transactions.timers.AuctionCountDown;
import server.softwarehouseacces.temps.Item;

import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ItemScanner {
	public void buyoutBought(Connection c, Item item) throws SQLException {
		PreparedStatement itemBoughtThruBuyout = c.prepareStatement(
						"UPDATE \"public\".buyout SET buyer = '" +
										item.getTempSaleStrategy().getUsernameFromBuyer() +
										"' WHERE itemID = " +
										item.getId()
		);

		//Indtil videre sætter den kun den købte data, herefter skal værdien endten fjernes eller flyttes

		itemBoughtThruBuyout.execute();
		itemBoughtThruBuyout.close();
		c.close();
	}

	public void auctionBought(Connection c, int itemID) throws SQLException {
		System.out.println("Ready to delete: " + itemID);                   //Skal fjernes senere
		String sql = "DELETE \"public\".auction WHERE itemID = " + itemID;

		PreparedStatement pstmt = c.prepareStatement(sql);
		pstmt.execute();

		pstmt.close();
		c.close();
	}

	public void newBid(Connection c, Item item) throws SQLException {
		PreparedStatement itemNewBidTruAuction = c.prepareStatement(
						"UPDATE \"public\".auction SET currentBid = " + item.getTempSaleStrategy().getOffer() +
										", currentBidder = '" + item.getTempSaleStrategy().getUsernameFromBuyer() + "'"
		);

		itemNewBidTruAuction.execute();

		itemNewBidTruAuction.close();
		c.close();
	}

	public void auctionTimers(Connection c, PropertyChangeListener listener) throws SQLException {
		LocalDateTime localTimeIn1Hour = LocalDateTime.now().plusHours(1);
		String localTimeInWantedStringFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(localTimeIn1Hour);

		PreparedStatement pstmt = c.prepareStatement(
						"SELECT itemID, auctionenddate FROM \"public\".Auction " +
										"WHERE auctionenddate < '" +
										localTimeInWantedStringFormat +
										"' ORDER BY itemid" //Order by noget andet potentielt?
		);

		ResultSet allAuctionThatFinishesBeforeAnHour = pstmt.executeQuery();



		auctionTimeSetter(allAuctionThatFinishesBeforeAnHour, listener, localTimeIn1Hour);
		pstmt.close();
		c.close();
	}

	private void auctionTimeSetter(ResultSet auctions, PropertyChangeListener listener, LocalDateTime localTimeIn1Hour) throws SQLException {
		while (auctions.next()) {
			new Thread(
							new AuctionCountDown(
											auctions.getInt("itemID"),
											auctions.getTimestamp("AuctionEndDate"),
											localTimeIn1Hour,
											listener)
			).start();
		}
	}

	//Remover identitity, virker som en hard reset for et table.
	public void clearTable(Connection c, String testTable) {
		try {
			PreparedStatement clearTableSQL = c.prepareStatement("TRUNCATE TABLE " +testTable+" RESTART IDENTITY");

			clearTableSQL.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}



	}
}
