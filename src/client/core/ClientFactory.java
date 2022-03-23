package client.core;

import client.network.Client;
import client.network.SocketClient;

// Singleton pattern
public class ClientFactory {
	private static final ClientFactory instance = new ClientFactory();
	private Client client;

	private ClientFactory() {
	}

	public static ClientFactory getInstance() {
		return instance;
	}

	public Client getClient() {
		if (client == null) {
			client = new SocketClient();
		}

		return client;
	}
}
