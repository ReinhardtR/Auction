package server.model;

import shared.transferobjects.AuctionItem;

public interface DatabaseIO {

	void addItemToAuction(AuctionItem item); //Indholder - Titel, - Beskrivelse, - Tags, - "Pris".
	void removeItemFromServer(AuctionItem item);


}
