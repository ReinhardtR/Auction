package server;

import server.model.ChatModel;
import server.network.SocketServer;

public class RunServer {
	public static void main(String[] args) {
		ChatModel chatModel = new ChatModel();
		SocketServer server = new SocketServer(chatModel);
		server.startServer();
	}
}
