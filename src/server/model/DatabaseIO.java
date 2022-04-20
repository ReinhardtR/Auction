package server.model;

import shared.transferobjects.AuctionItem;

import java.sql.SQLException;

public interface DatabaseIO {

	void addItemToAuction(AuctionItem item); //Indholder - Titel, - Beskrivelse, - Tags, - "Pris".
	void removeItemFromServer(AuctionItem item) throws SQLException;
	void updateHighestBidder(AuctionItem item) throws SQLException;


}
