package server;

import server.model.ChatModel;
import server.network.Pool;
import server.network.SocketServer;

public class RunServer {
	public static void main(String[] args) {
		Pool pool = new Pool();
		ChatModel chatModel = new ChatModel();
		SocketServer server = new SocketServer(chatModel, pool);
		server.startServer();
	}
}
