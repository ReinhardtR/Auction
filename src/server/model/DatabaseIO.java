package server.model;

import server.model.auction.Item;
import server.model.temps.TempBuyout;
import server.model.temps.TempItem;
import shared.transferobjects.AuctionItem;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DatabaseIO {
/*
	void addItemToAuction(String relation, AuctionItem item);

	void removeItemFromServer(String relation, AuctionItem item) throws SQLException;

	void updateHighestBidder(AuctionItem item) throws SQLException;

	void clearTable(String relation) throws SQLException;

	int getLatestId(String relation) throws SQLException;

	ArrayList<AuctionItem> searchAuctionItemsFromKeyword(String keyword, String relation) throws SQLException;



 */
	 Item getItem(int itemID);

	 void buyoutItemBought(TempItem itemID);

	 void updateItemOffer(TempItem item);


	void clearTable(String testTable) throws SQLException;
}
