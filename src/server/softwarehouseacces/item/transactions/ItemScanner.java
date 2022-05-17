package server.softwarehouseacces.item.transactions;


import server.model.item.ItemImpl;
import server.softwarehouseacces.item.transactions.timers.AuctionCountDown;
import server.softwarehouseacces.utils.SQL;

import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ItemScanner {
	public void buyoutBought(Connection c, ItemImpl item) throws SQLException {
		PreparedStatement itemBoughtThruBuyout = null;
		try {
			itemBoughtThruBuyout = c.prepareStatement(
							SQL.buyoutBought(item.getItemID(), item.getBuyerUsername()
							)
			);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		//Indtil videre sætter den kun den købte data, herefter skal værdien endten fjernes eller flyttes

		assert itemBoughtThruBuyout != null;
		itemBoughtThruBuyout.execute();
		itemBoughtThruBuyout.close();
		c.close();
	}

	public void auctionBought(Connection c, String itemID) throws SQLException {
		System.out.println("Ready to delete: " + itemID);                   //Skal fjernes senere

		PreparedStatement pstmt = c.prepareStatement(SQL.auctionBought(itemID));
		pstmt.execute();

		pstmt.close();
		c.close();
	}


	public void newBid(Connection c, ItemImpl item) throws SQLException {
		PreparedStatement itemNewBidTruAuction = null;
		try {
			itemNewBidTruAuction = c.prepareStatement(
							SQL.auctionNewBid(item.getItemID(), item.getOfferAmount(), item.getBuyerUsername())
			);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		assert itemNewBidTruAuction != null;
		itemNewBidTruAuction.execute();

		itemNewBidTruAuction.close();
		c.close();
	}

	public void auctionTimers(Connection c, PropertyChangeListener listener) throws SQLException {
		LocalDateTime localTimeIn1Hour = LocalDateTime.now().plusHours(1);
		String localTimeInWantedStringFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(localTimeIn1Hour);

		PreparedStatement pstmt = c.prepareStatement(
						SQL.auctionsSoonToFinish(localTimeInWantedStringFormat)
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
											auctions.getString("itemID"),
											auctions.getTimestamp("AuctionEndDate").toLocalDateTime(),
											localTimeIn1Hour,
											listener)
			).start();
		}
	}

	//Remover identitity, virker som en hard reset for et table.
	public void clearTable(Connection c, String testTable) {
		try {
			PreparedStatement clearTableSQL = c.prepareStatement("TRUNCATE TABLE " + testTable + " RESTART IDENTITY");

			clearTableSQL.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}


	}
}
