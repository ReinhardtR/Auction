package server.model;

import shared.transferobjects.Item;

public interface DatabaseIO {

	void addItemToAuction(Item item); //Indholder - Titel, - Beskrivelse, - Tags, - "Pris".
	void removeItemFromServer(Item item);


}
