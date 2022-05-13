package server.model;

import server.model.auction.Item;
import server.model.temps.TempBuyout;
import server.model.temps.TempItem;
import shared.transferobjects.AuctionItem;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DatabaseIO {

	 Item getItem(int itemID);

	 void buyoutItemBought(TempItem itemID);

	 void updateItemOffer(TempItem item);


	void clearTable(String testTable) throws SQLException;
}
