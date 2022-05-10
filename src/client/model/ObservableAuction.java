package client.model;

import client.network.LocalClient;
import shared.model.Auction;
import shared.model.Auctioneer;
import shared.model.Bid;
import shared.utils.PropertyChangeSubject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;

// viewmodel calls setbid on model ->
// model calls setbidOnAuction on client ->
// client calls setbid on server model ->
// server model calls onNewBid on all clients ->
// clients fire property change ->
// models forward the property change to viewmodels ->
// viewmodels call get bid on model ->

public class ObservableAuction implements Auction, PropertyChangeListener, PropertyChangeSubject {
	private final String itemID;
	private final PropertyChangeSupport support;
	private final LocalClient client;
	private final Auction auction;

	public ObservableAuction(LocalClient client, Auction auction) throws RemoteException {
		support = new PropertyChangeSupport(this);

		this.client = client;
		this.auction = auction;

		// Cache itemID
		itemID = auction.getItemID();

		client.addListener(itemID, this);
	}

	@Override
	public String getItemID() throws RemoteException {
		return auction.getItemID();
	}

	@Override
	public Bid getBid() throws RemoteException {
		return auction.getBid();
	}

	@Override
	public void setBid(Bid bid) throws RemoteException {
		auction.setBid(bid);
	}

	@Override
	public Auctioneer getAuctioneer() throws RemoteException {
		return auction.getAuctioneer();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		support.firePropertyChange(itemID, null, null);
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
