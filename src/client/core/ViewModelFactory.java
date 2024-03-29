package client.core;

import client.views.auction.AuctionViewModel;
import client.views.auction.AuctionViewModelImpl;
import client.views.buyout.BuyoutViewModel;
import client.views.buyout.BuyoutViewModelImpl;
import client.views.itemlist.ItemListViewModel;
import client.views.itemlist.ItemListViewModelImpl;
import client.views.login.LoginViewModel;
import client.views.login.LoginViewModelImpl;
import client.views.sale.SaleViewModel;
import client.views.sale.SaleViewModelImpl;

public class ViewModelFactory {
	private static final ViewModelFactory instance = new ViewModelFactory();

	private ViewModelFactory() {
	}

	public static ViewModelFactory getInstance() {
		return instance;
	}

	public ItemListViewModel getItemListViewModel() {
		return new ItemListViewModelImpl(ModelFactory.getInstance().getObservableItemList());
	}

	public AuctionViewModel getAuctionViewModel() {
		return new AuctionViewModelImpl(
						ModelFactory.getInstance().getUser(),
						ModelFactory.getInstance().getObservableItemList().getCurrentlyViewedItem()
		);
	}

	public BuyoutViewModel getBuyoutViewModel() {
		return new BuyoutViewModelImpl(
						ModelFactory.getInstance().getUser(),
						ModelFactory.getInstance().getObservableItemList().getCurrentlyViewedItem()
		);
	}

	public LoginViewModel getLoginViewModel() {
		return new LoginViewModelImpl(ModelFactory.getInstance().getUser());
	}

	public SaleViewModel getSaleViewModel() {
		return new SaleViewModelImpl(ModelFactory.getInstance().getUser());
	}
}
