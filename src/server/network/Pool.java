package server.network;

import shared.transferobjects.Message;

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

	public void broadcast(Message message) {
		for (SocketHandler socketHandler : connections) {
			socketHandler.sendMessage(message);
		}
	}
}
