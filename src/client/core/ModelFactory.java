package client.core;

import java.rmi.RemoteException;

public class ModelFactory {
	private static final ModelFactory instance = new ModelFactory();
	private AuctionHouseModel auctionHouseModel;
	private AuctionModel auctionModel;

	private ModelFactory() {
	}

	public static ModelFactory getInstance() {
		return instance;
	}

	public AuctionHouseModel getAuctionHouseModel() {
		if (auctionHouseModel == null) {
			auctionHouseModel = new AuctionHouseModel(ClientFactory.getInstance().getClient());
		}

		return auctionHouseModel;
	}

	public AuctionModel getAuctionModelTest() {
		if (auctionModel == null) {
			MainClientHandler client = ClientFactory.getInstance().getClient();

			try {
				auctionModel = new AuctionModel(client, client.getAuctions().get(0));
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		return auctionModel;
	}
}
