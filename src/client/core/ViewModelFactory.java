package client.core;

import client.views.auction.AuctionViewModel;
import client.views.auction.AuctionViewModelImpl;
import client.views.buyout.BuyoutViewModel;
import client.views.buyout.BuyoutViewModelImpl;
import client.views.itemlist.ItemListViewModel;
import client.views.itemlist.ItemListViewModelImpl;

public class ViewModelFactory {
	private static final ViewModelFactory instance = new ViewModelFactory();

	private ItemListViewModel itemListViewModel;
	private AuctionViewModel auctionViewModel;
	private BuyoutViewModel buyoutViewModel;

	private ViewModelFactory() {
	}

	public static ViewModelFactory getInstance() {
		return instance;
	}

	public ItemListViewModel getItemListViewModel() {
		if (itemListViewModel == null) {
			itemListViewModel = new ItemListViewModelImpl(ModelFactory.getInstance().getObservableItemList());
		}
		return itemListViewModel;
	}

	public AuctionViewModel getAuctionViewModel() {
		if (auctionViewModel == null) {
			auctionViewModel = new AuctionViewModelImpl(ModelFactory.getInstance().getObservableItemList().getCurrentlyViewedItem());
		}

		return auctionViewModel;
	}

	public BuyoutViewModel getBuyoutViewModel() {
		if (buyoutViewModel == null) {
			buyoutViewModel = new BuyoutViewModelImpl(ModelFactory.getInstance().getObservableItemList().getCurrentlyViewedItem());
		}

		return buyoutViewModel;
	}
}
