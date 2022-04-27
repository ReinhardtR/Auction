package client.network;

import shared.network.client.AuctionData;
import shared.network.client.Client;
import shared.network.server.Server;
import shared.transferobjects.AuctionBid;
import shared.utils.PropertyChangeSubject;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class MainClientHandler implements Client, PropertyChangeSubject, Remote {
	private final PropertyChangeSupport support;
	private Server server;

	public MainClientHandler() {
		support = new PropertyChangeSupport(this);
		try {
			UnicastRemoteObject.exportObject(this, 0);
			server = ((Server) LocateRegistry.getRegistry(1099).lookup("Server"));
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}

	public void makeNewBid(AuctionBid auctionBid) {
		try {
			server.getActionHouse().getAuctionManager(auctionBid.getItemId()).newAuctionBid(auctionBid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onNewBid(AuctionBid auctionBid) throws RemoteException {
		System.out.println("NEW BID ON CLIENT");
		String propertyName = "NEW_BID:" + auctionBid.getItemId();
		support.firePropertyChange(propertyName, null, auctionBid);
	}

	@Override
	public List<AuctionData> getAuctions() throws RemoteException {
		return server.getActionHouse().getAuctions();
	}

	@Override
	public void watchAuction(String auctionId) throws RemoteException {
		try {
			server.getActionHouse().getAuctionManager(auctionId).registerClient(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
