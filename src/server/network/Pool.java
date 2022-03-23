package server.network;

import java.util.ArrayList;
import java.util.List;

public class Pool {
	private final List<SocketHandler> connections;

	public Pool() {
		connections = new ArrayList<>();
	}

	public void addConnection(SocketHandler socketHandler) {
		connections.add(socketHandler);
	}

	public void broadcast(String msg) {
		for (SocketHandler socketHandler : connections) {
			socketHandler.sendMessage(msg);
		}
	}
}
