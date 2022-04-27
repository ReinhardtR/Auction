package client.core;

import client.views.auction.AuctionViewModel;
import client.views.login.LoginViewModel;

public class ViewModelFactory {
	private static final ViewModelFactory instance = new ViewModelFactory();
	private LoginViewModel loginViewModel;
	private AuctionViewModel auctionViewModel;

	private ViewModelFactory() {
	}

	public static ViewModelFactory getInstance() {
		return instance;
	}

	public LoginViewModel getLoginViewModel() {
		if (loginViewModel == null) {
			loginViewModel = new LoginViewModel();
		}

		return loginViewModel;
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
