package shared.network.client;

import shared.transferobjects.AuctionBid;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Client extends Remote {
	void onNewBid(AuctionBid bid) throws RemoteException;

	List<AuctionData> getAuctions() throws RemoteException;

	void watchAuction(String auctionId) throws RemoteException;
}
