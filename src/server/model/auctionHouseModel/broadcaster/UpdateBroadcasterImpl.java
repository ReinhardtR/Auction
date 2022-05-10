package server.model.auctionHouseModel.broadcaster;

import client.model.ObservableItem;
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
	private final List<SharedClient> listeners;
	private final String itemID;

	private UpdateBroadcasterImpl(String itemID) throws RemoteException {
		listeners = new ArrayList<>();
		this.itemID = itemID;
	}

	public static UpdateBroadcasterImpl getBroadcasterInstance(String itemID) throws RemoteException {
		UpdateBroadcasterImpl broadcaster = instances.get(itemID);
		if(broadcaster == null)
		{
			synchronized (instances)
			{
				broadcaster = instances.get(itemID);
				if(broadcaster == null)
				{
					System.out.println("NEW BROADCASTER WITH KEY: " + itemID);
					broadcaster = new UpdateBroadcasterImpl(itemID);
					instances.put(itemID, broadcaster);
				}
			}
		}
		return broadcaster;
	}

	public void broadcast(ObservableItem item) {
		System.out.println("BROADCAST");
		System.out.println(listeners.size());
		listeners.forEach((listener) -> {
			try {
				System.out.println("AM BROADCASTING");
				listener.onNewBid(itemID);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public void addListener(SharedClient client) throws RemoteException {
		System.out.println("LISTENER BEING ADDED");
		listeners.add(client);
		System.out.println(listeners.size());
	}

	@Override
	public void removeListener(SharedClient client) throws RemoteException {
		listeners.remove(client);
	}
}
