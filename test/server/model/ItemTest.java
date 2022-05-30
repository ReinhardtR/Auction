package server.model;

import client.model.item.ItemCacheProxyImpl;
import client.model.item.ObservableItem;
import client.views.auction.AuctionViewModel;
import client.views.auction.AuctionViewModelImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.model.item.ItemImpl;
import server.model.item.sale_strategy.AuctionStrategy;
import shared.SaleStrategyType;
import shared.network.model.Item;

import java.rmi.RemoteException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ItemTest {
	private Item testItem;
	private ObservableItem observableTestItem;
	private AuctionViewModel auctionViewModel;

	@BeforeEach
	void createTestItem() throws RemoteException {
		testItem = new ItemImpl("123", "Mand", "title", "description", "tags", new AuctionStrategy(0, "tis", LocalDateTime.of(2022, 7, 7, 11, 20)));
		observableTestItem = new ObservableItem(null, new ItemCacheProxyImpl(testItem));

		auctionViewModel = new AuctionViewModelImpl(null, observableTestItem);
	}

	//UserSale Strategy ZOMBIES
	@Test
	@DisplayName("Testing currentBid increases with higher offers")
	public void auctionBiddingUpTest() throws RemoteException {
		auctionViewModel.bidOnItem("-20");
		assertEquals(0, testItem.getOfferAmount());

		auctionViewModel.bidOnItem("0");
		assertEquals(0, testItem.getOfferAmount());

		auctionViewModel.bidOnItem("200");
		assertEquals(200, testItem.getOfferAmount());
	}

	@Test
	@DisplayName("Testing currentBid does not increase with a offer not high enough")
	public void toLowBidTest() throws RemoteException {
		observableTestItem.userSaleStrategy(2000, "TestName1");
		assertEquals(2000, testItem.getOfferAmount());

		observableTestItem.userSaleStrategy(1000, "TestName2");
		assertEquals(2000, testItem.getOfferAmount());
	}

	@Test
	@DisplayName("Testing boundaries for the offer")
	public void boundaryTest() throws RemoteException {
		observableTestItem.userSaleStrategy(-20, "TestName1");
		assertEquals(0, testItem.getOfferAmount());

		observableTestItem.userSaleStrategy(Integer.MAX_VALUE, "TestName2");
		assertEquals(Integer.MAX_VALUE, testItem.getOfferAmount());
	}


	@Test
	@DisplayName("Strategy type test")
	public void strategyTypeTest() throws RemoteException {
		assertEquals(observableTestItem.getStrategyType(), SaleStrategyType.AUCTION);
	}


}
