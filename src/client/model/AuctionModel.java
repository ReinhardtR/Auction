package client.model;

import client.network.SocketClient;
import shared.transferobjects.AuctionBid;
import shared.util.PropertyChangeSubject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class AuctionModel implements PropertyChangeSubject {
	private final SocketClient client;
	private final PropertyChangeSupport support;

	public AuctionModel(SocketClient client) {
		support = new PropertyChangeSupport(this);

		this.client = client;
		client.addListener("NEW_AUCTION_BID", this::onNewAuctionBid);
	}

	public void makeNewBid(AuctionBid auctionBid) {
		client.sendAuctionBid(auctionBid);
	}

	public void onNewAuctionBid(PropertyChangeEvent event) {
		AuctionBid auctionBid = (AuctionBid) event.getNewValue();

		support.firePropertyChange("NEW_AUCTION_BID", null, auctionBid);
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
