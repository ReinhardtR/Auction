package server.persistence.item.adderToDatabase;

import server.model.item.ItemImpl;
import server.persistence.utils.SQL;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;

public class AdderToDatabase {


	//Grim metode, re-think ud fra tanken om at vi ikke ville lave if-else statements.
	public void addItemToDatabase(Connection connection, ItemImpl itemToAdd) {


		try {
			if (itemToAdd.getStrategyType().toString().equalsIgnoreCase("auction"))
			{
				connection.prepareStatement(SQL.addAuctionItem(itemToAdd.getOfferAmount(),itemToAdd.getEndTimestamp(),itemToAdd.getStrategyType()));
				//Til ovenstående metode skal sælgerens username addes.
			}
			else if (itemToAdd.getItemID().toString().equalsIgnoreCase("buyout"))
			{
				connection.prepareStatement(SQL.addBuyoutItem(itemToAdd.getOfferAmount(),itemToAdd.getStrategyType()));
				//Til ovenståendem etode skal sælgerens username addes.
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//Lave metode som bliver kaldt på database ud fra de informationer som ligger hos itemToAdd
	}


}
