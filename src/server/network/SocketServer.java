package server.network;

import server.model.Chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

	private final Chat chat;
	private final Pool pool;

	public SocketServer(Chat chat, Pool pool) {
		this.pool = pool;
		this.chat = chat;
	}

	public void startServer() {
		System.out.println("Starting server...");
		try {
			ServerSocket welcomeSocket = new ServerSocket(2910);

			while (true) {
				Socket socket = welcomeSocket.accept();

				SocketHandler client = new SocketHandler(socket, pool, chat);
				pool.addConnection(client);

				new Thread(client).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
