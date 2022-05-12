package client.core;

import client.views.auction.AuctionViewModel;
import client.views.buyout.BuyoutViewModel;
import client.views.item_list.ItemListViewModel;

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
			itemListViewModel = new ItemListViewModel(ModelFactory.getInstance().getObservableItemList());
		}
		return itemListViewModel;
	}

	public AuctionViewModel getAuctionViewModel() {
		if (auctionViewModel == null) {
			auctionViewModel = new AuctionViewModel(ModelFactory.getInstance().getObservableItemList());
		}

		return auctionViewModel;
	}

	public BuyoutViewModel getBuyoutViewModel() {
		if (buyoutViewModel == null) {
			buyoutViewModel = new BuyoutViewModel(ModelFactory.getInstance().getObservableItemList());
		}

		return buyoutViewModel;
	}
}
