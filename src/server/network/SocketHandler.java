package server.network;

import server.model.auction.Auction;
import server.model.auction.AuctionHouse;
import shared.transferobjects.AuctionBid;
import shared.transferobjects.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketHandler implements Runnable {

	private final AuctionHouse auctionHouse;
	private final Socket socket;
	private final Pool pool;
	private ObjectOutputStream outToClient;
	private ObjectInputStream inFromClient;

	public SocketHandler(Socket socket, Pool pool, AuctionHouse auctionHouse) {
		this.socket = socket;
		this.pool = pool;
		this.auctionHouse = auctionHouse;

		try {
			inFromClient = new ObjectInputStream(socket.getInputStream());
			outToClient = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			while (true) {
				Request request = (Request) inFromClient.readObject();
				System.out.println("RECEIVED REQUEST");

				if (request.getType().equals("NEW_AUCTION_BID")) {
					AuctionBid auctionBid = (AuctionBid) request.getArg();
					Auction auction = auctionHouse.getAuction(auctionBid.getItemId());

					try {
						auction.setBid(auctionBid);
						Request response = new Request("NEW_AUCTION_BID", auctionBid);
						pool.broadcast(response);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

//				if (request.getType().equals("NEW_MESSAGE")) {
//					Message message = (Message) request.getArg();
//					pool.broadcast(message);
//					System.out.println(message.getContent());
//				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendRequest(Request request) {
		try {
			outToClient.writeObject(request);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
