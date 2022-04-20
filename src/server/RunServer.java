package server;

import server.model.auction.Auction;
import server.model.auction.AuctionHouse;
import server.model.auction.AuctionManager;
import server.model.auction.Item;
import server.network.Pool;
import server.network.SocketServer;

public class RunServer {
	public static void main(String[] args) {
		Pool pool = new Pool();
		
		Auction auction = new AuctionManager(new Item("123"));
		AuctionHouse auctionHouse = new AuctionHouse();
		auctionHouse.addAuction(auction);

		SocketServer server = new SocketServer(auctionHouse, pool);
		server.startServer();
	}
}
