package server.model;

import shared.network.client.SharedClient;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UpdateBroadcasterImpl extends UnicastRemoteObject implements UpdateBroadcaster {
	private static final Map<String, UpdateBroadcasterImpl> instances = new HashMap<>();
	private static final Lock lock = new ReentrantLock();
	private final List<SharedClient> listeners;
	private final String itemID;

	private UpdateBroadcasterImpl(String itemID) throws RemoteException {
		listeners = new ArrayList<>();
		this.itemID = itemID;
	}

	public static UpdateBroadcasterImpl getInstance(String itemID) throws RemoteException {
		UpdateBroadcasterImpl broadcaster = instances.get(itemID);

		if (broadcaster == null) {
			synchronized (lock) {
				if (broadcaster == null) {
					try {
						broadcaster = new UpdateBroadcasterImpl(itemID);
						instances.put(itemID, broadcaster);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			}
		}

		return broadcaster;
	}

	public void broadcast() {
		listeners.forEach((listener) -> {
			try {
				listener.onNewBid(itemID);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public void addListener(SharedClient client) throws RemoteException {
		listeners.add(client);
	}

	@Override
	public void removeListener(SharedClient client) throws RemoteException {
		listeners.remove(client);
	}
}
