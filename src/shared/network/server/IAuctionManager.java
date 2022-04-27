package shared.network.server;

import shared.network.client.AuctionData;
import shared.network.client.Client;
import shared.transferobjects.AuctionBid;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAuctionManager extends Remote {

	AuctionData getAuction() throws RemoteException;

	void newAuctionBid(AuctionBid auctionBid) throws RemoteException;

	void registerClient(Client client) throws RemoteException;
}
