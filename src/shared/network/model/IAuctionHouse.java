package shared.network.model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public interface IAuctionHouse extends Remote {

	IAuctionManager getAuctionManager(String key) throws RemoteException, Exception;

	List<AuctionData> getAuctions() throws RemoteException;

	HashMap<String, IAuctionManager> getAuctionManagers() throws RemoteException;
}
