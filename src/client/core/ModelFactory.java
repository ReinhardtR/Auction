package client.core;

import client.model.AuctionModel;

public class ModelFactory {
	private static final ModelFactory instance = new ModelFactory();
	private AuctionModel auctionModel;

	private ModelFactory() {
	}

	public static ModelFactory getInstance() {
		return instance;
	}
	
	public AuctionModel getAuctionModel() {
		if (auctionModel == null) {
			auctionModel = new AuctionModel(ClientFactory.getInstance().getClient());
		}

		return auctionModel;
	}
}
