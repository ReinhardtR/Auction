package shared.network.server;

import shared.network.client.AuctionData;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

public interface IAuctionHouse extends Remote {

	IAuctionManager getAuctionManager(String key) throws RemoteException, Exception;

	List<AuctionData> getAuctions() throws RemoteException;

	HashMap<String, IAuctionManager> getAuctionManagers() throws RemoteException;
}
