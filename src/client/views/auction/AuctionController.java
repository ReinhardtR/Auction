package client.views.auction;

import client.core.ViewHandler;

public class AuctionController {
	private AuctionViewModel auctionViewModel;
	private ViewHandler viewHandler;

	public void init(AuctionViewModel auctionViewModel, ViewHandler viewHandler) {
		this.auctionViewModel = auctionViewModel;
		this.viewHandler = viewHandler;
	}
}
