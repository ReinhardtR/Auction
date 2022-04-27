package client.model;

import client.network.MainClientHandler;
import shared.network.client.AuctionData;
import shared.transferobjects.AuctionBid;
import shared.utils.PropertyChangeSubject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class AuctionHouseModel implements PropertyChangeSubject {
	private final MainClientHandler client;
	private final PropertyChangeSupport support;
	private List<AuctionData> auctions;

	public AuctionHouseModel(MainClientHandler client) {
		support = new PropertyChangeSupport(this);

		this.client = client;
		this.client.addListener("NEW_BID", this::onNewBid);

		try {
			this.auctions = client.getAuctions();
		} catch (RemoteException e) {
			e.printStackTrace();
			this.auctions = new ArrayList<>();
		}
	}


	public void onNewBid(PropertyChangeEvent event) {
		AuctionBid auctionBid = (AuctionBid) event.getNewValue();
		String propertyName = "NEW_BID" + "_" + auctionBid.getItemId();
		support.firePropertyChange(propertyName, null, auctionBid);
	}

	@Override
	public void addListener(String eventName, PropertyChangeListener listener) {
		support.addPropertyChangeListener(eventName, listener);
	}

	@Override
	public void removeListener(String eventName, PropertyChangeListener listener) {
		support.removePropertyChangeListener(eventName, listener);
	}
}
