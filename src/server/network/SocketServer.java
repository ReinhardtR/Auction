package server.network;

import server.model.auction.AuctionHouse;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

	private final AuctionHouse auctionHouse;
	private final Pool pool;

	public SocketServer(AuctionHouse auctionHouse, Pool pool) {
		this.pool = pool;
		this.auctionHouse = auctionHouse;
	}

	public void startServer() {
		System.out.println("Starting server...");
		try {
			ServerSocket welcomeSocket = new ServerSocket(2910);

			while (true) {
				Socket socket = welcomeSocket.accept();

				SocketHandler client = new SocketHandler(socket, pool, auctionHouse);
				pool.addConnection(client);

				new Thread(client).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
