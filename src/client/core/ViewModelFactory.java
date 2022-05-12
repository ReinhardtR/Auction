package client.core;

import client.views.auction.AuctionViewModel;
import client.views.auctionsListe.AuctionsListeViewModel;
import client.views.buyout.BuyoutViewModel;

public class ViewModelFactory {
	private static final ViewModelFactory instance = new ViewModelFactory();
	private AuctionViewModel auctionViewModel;
	private AuctionsListeViewModel auctionsListeViewModel;
	private BuyoutViewModel buyoutViewModel;

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

	public AuctionsListeViewModel getAuctionsListeViewModel() {
		if (auctionsListeViewModel == null) {
			auctionsListeViewModel = new AuctionsListeViewModel(ModelFactory.getInstance().getAuctionModelTest());
		}
		return auctionsListeViewModel;
	}

	public BuyoutViewModel getBuyoutViewModel() {
		if (buyoutViewModel == null) {
			buyoutViewModel = new BuyoutViewModel(ModelFactory.getInstance().getAuctionModelTest());
		}
		return buyoutViewModel;
	}
}
