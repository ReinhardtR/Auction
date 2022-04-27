package client.network;

import server.model.auction.Item;
import shared.network.Auction;
import shared.network.client.Client;
import shared.network.server.Server;
import shared.transferobjects.AuctionBid;
import shared.utils.PropertyChangeSubject;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class MainClientHandler implements Client, Auction, PropertyChangeSubject {

	PropertyChangeSupport support;
	Server server;

	public MainClientHandler() {
		support = new PropertyChangeSupport(this);
		try {
			UnicastRemoteObject.exportObject(this, 0);
			server = ((Server) LocateRegistry.getRegistry(1099).lookup("Server"));
			joinAuction(this);
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}

	public void makeNewBid(AuctionBid auctionBid) {
		try {
			server.getActionHouse().getAuction("123").newAuctionBid(auctionBid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void newAuctionBid(AuctionBid auctionBid) throws RemoteException {
		support.firePropertyChange("NEW_AUCTION_BID", null, auctionBid);
	}

	@Override
	public void joinAuction(Auction listener) throws RemoteException {
		try {
			server.getActionHouse().getAuction("123").joinAuction(listener);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Item getItem() throws RemoteException {
		return null;
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
