package client.model;

import client.network.MainClientHandler;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import shared.network.client.AuctionData;
import shared.transferobjects.AuctionBid;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;

public class AuctionModel {
	private final MainClientHandler client;
	private final AuctionData auctionData;
	private final ObjectProperty<AuctionBid> bidProperty;

	public AuctionModel(MainClientHandler client, AuctionData auctionData) {
		this.auctionData = auctionData;

		this.client = client;
		String propertyName = "NEW_BID:" + auctionData.getItem().getId();
		this.client.addListener(propertyName, this::onNewBid);

		bidProperty = new SimpleObjectProperty<>(auctionData.getBid());

		try {
			client.watchAuction(auctionData.getItem().getId());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void makeNewBid(AuctionBid auctionBid) {
		client.makeNewBid(auctionBid);
	}

	private void onNewBid(PropertyChangeEvent event) {
		System.out.println("NEW BID ON MODEL");
		AuctionBid bid = (AuctionBid) event.getNewValue();

		bidProperty.setValue(bid);
		System.out.println(bid.getAmount() + " " + bidProperty.getValue().getAmount());
	}

	public ObjectProperty<AuctionBid> bidProperty() {
		return bidProperty;
	}
}
