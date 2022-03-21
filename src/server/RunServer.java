package server;

import server.model.ChatModel;
import server.network.SocketServer;

public class RunServer {
	public static void main(String[] args) {
		SocketServer socketServer = new SocketServer(new ChatModel());
		socketServer.startServer();
	}
}
