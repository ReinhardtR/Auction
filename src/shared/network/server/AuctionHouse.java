package shared.network.server;

import shared.network.Auction;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AuctionHouse extends Remote {

	Auction getAuction(String key) throws RemoteException, Exception;

}
