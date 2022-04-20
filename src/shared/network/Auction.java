package shared.network;

import server.model.auction.Item;
import shared.transferobjects.AuctionBid;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Auction extends Remote {

	void newActionBid(AuctionBid auctionBid) throws RemoteException;

	void joinAuction(Auction listener) throws RemoteException;

	Item getItem() throws RemoteException;
}
