package client.core;

import client.views.auction.AuctionViewModel;

public class ViewModelFactory {
	private static final ViewModelFactory instance = new ViewModelFactory();
	private AuctionViewModel auctionViewModel;

	private ViewModelFactory() {
	}

	public static ViewModelFactory getInstance() {
		return instance;
	}

//	public AuctionViewModel getAuctionViewModel() {
//		if (auctionViewModel == null) {
//			auctionViewModel = new AuctionViewModel(ModelFactory.getInstance().getAuctionHouseModel());
//		}
//
//		return auctionViewModel;
//	}

	public AuctionViewModel getAuctionViewModel() {
		if (auctionViewModel == null) {
			auctionViewModel = new AuctionViewModel(ModelFactory.getInstance().getAuctionModelTest());
		}

		return auctionViewModel;
	}
}
