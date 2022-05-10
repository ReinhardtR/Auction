package server.model.auction;

import shared.network.model.IAuctionHouse;
import shared.network.model.IAuctionManager;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AuctionHouse extends UnicastRemoteObject implements IAuctionHouse {
	HashMap<String, IAuctionManager> auctionManagers;

	public AuctionHouse() throws RemoteException {
		auctionManagers = new HashMap<>();
	}

	@Override
	public IAuctionManager getAuctionManager(String itemId) throws RemoteException, Exception {
		return auctionManagers.get(itemId);
	}

	public List<AuctionData> getAuctions() {
		List<AuctionData> auctions = new ArrayList<>();

		auctionManagers.forEach((itemId, auctionManager) -> {
			try {
				auctions.add(auctionManager.getAuction());
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});

		return auctions;
	}

	@Override
	public HashMap<String, IAuctionManager> getAuctionManagers() throws RemoteException {
		return null;
	}

	public void addAuction(IAuctionManager auctionManager) {
		try {
			auctionManagers.put(auctionManager.getAuction().getItem().getId(), auctionManager);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
