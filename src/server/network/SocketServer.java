package server.network;

import server.model.Chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketServer {

	private final Chat chat;

	public SocketServer(Chat chat) {
		this.chat = chat;
	}

	public void startServer() {
		System.out.println("Starting server...");
		try {
			ServerSocket welcomeSocket = new ServerSocket(2910);
			List<SocketHandler> handlers = new ArrayList<>();

			while (true) {
				Socket socket = welcomeSocket.accept();
				SocketHandler handler = new SocketHandler(socket, chat);
				handlers.add(handler);

				System.out.println("handlers:" + handlers.size());

				new Thread(handler).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
