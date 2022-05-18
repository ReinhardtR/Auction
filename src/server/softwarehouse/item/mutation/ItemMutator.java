package server.softwarehouse.item.mutation;


import server.model.item.ItemImpl;
import server.softwarehouse.item.mutation.timers.AuctionCountDown;
import server.softwarehouse.utils.SQL;

import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ItemMutator {
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
		if (itemBoughtThruBuyout == null) throw new SQLException("itemBoughtThruBuyout was null");
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

		if (itemNewBidTruAuction == null) throw new SQLException("itemNewBidTruAuction was null");
		itemNewBidTruAuction.execute();
		itemNewBidTruAuction.close();

		c.close();
	}

	public void auctionTimers(Connection c, PropertyChangeListener listener) throws SQLException {
		LocalDateTime localTimeNow = LocalDateTime.now();
		String localTimeInWantedStringFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(localTimeNow.plusHours(1));

		PreparedStatement pstmt = c.prepareStatement(
						SQL.auctionsSoonToFinish(localTimeInWantedStringFormat)
		);
		ResultSet allAuctionThatFinishesBeforeAnHour = pstmt.executeQuery();

		auctionTimeSetter(allAuctionThatFinishesBeforeAnHour, listener, localTimeNow);
		pstmt.close();
		c.close();
	}

	private void auctionTimeSetter(ResultSet auctions, PropertyChangeListener listener, LocalDateTime localTimeNow) throws SQLException {
		while (auctions.next()) {
			new Thread(
							new AuctionCountDown(
											auctions.getString("itemID"),
											auctions.getTimestamp("AuctionEndDate").toLocalDateTime(),
											localTimeNow,
											listener)
			).start();
		}
	}
}
