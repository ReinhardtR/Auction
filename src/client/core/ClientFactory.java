package client.core;

import client.network.ClientImpl;
import client.network.LocalClient;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

// Singleton pattern
public class ClientFactory {
	private static final ClientFactory instance = new ClientFactory();
	private LocalClient client;

	private ClientFactory() {
	}

	public static ClientFactory getInstance() {
		return instance;
	}

	public LocalClient getClient() {
		if (client == null) {
			try {
				client = new ClientImpl();
			} catch (RemoteException | NotBoundException e) {
				e.printStackTrace();
			}
		}

		return client;
	}
}
