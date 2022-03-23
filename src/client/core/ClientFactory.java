package client.core;

import client.network.SocketClient;

// Singleton pattern
public class ClientFactory {
	private static final ClientFactory instance = new ClientFactory();
	private SocketClient client;

	private ClientFactory() {
	}

	public static ClientFactory getInstance() {
		return instance;
	}

	public SocketClient getClient() {
		if (client == null) {
			client = new SocketClient();
		}

		return client;
	}
}
