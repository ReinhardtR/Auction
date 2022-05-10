package client.core;

// Singleton pattern
public class ClientFactory {
	private static final ClientFactory instance = new ClientFactory();
	private MainClientHandler client;

	private ClientFactory() {
	}

	public static ClientFactory getInstance() {
		return instance;
	}

	public MainClientHandler getClient() {
		if (client == null) {
			client = new MainClientHandler();
		}

		return client;
	}
}
