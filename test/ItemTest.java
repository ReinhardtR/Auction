import client.model.ItemCalculations;
import static org.junit.jupiter.api.Assertions.*;

import client.model.ObservableItem;
import client.network.ClientImpl;
import client.network.LocalClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import server.model.item.Item;
import server.model.item.ItemImpl;
import server.model.item.SaleStrategy.AuctionStrategy;
import shared.network.model.GenerelItems;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalDateTime;


public class ItemTest {
	private Item testItem;
	private Item brokenTestItem;
	private GenerelItems observableTestItem;
	@BeforeEach
	void createTestItem() throws RemoteException {
			testItem = new ItemImpl("123", LocalDateTime.of(2022, 5, 16, 11, 20), new AuctionStrategy(0));
			brokenTestItem = new ItemImpl("123412414fqwfd123",LocalDateTime.of(200002,234,123,541252,234),null);

			observableTestItem = new ObservableItem(null,testItem);

	}

	@Test
	@DisplayName("Testing currentBid increases for every new offer")
	public void auctionBiddingUpTest() throws RemoteException {
		assertTrue(testItem.getOfferAmount() == 0);

		observableTestItem.userSaleStrategy(20,"TestName1");
		assertTrue(testItem.getOfferAmount() == 20);

		observableTestItem.userSaleStrategy(2000,"TestName2");
		assertTrue(testItem.getOfferAmount() == 2000);

		observableTestItem.userSaleStrategy(1000,"TestName1");
		assertTrue(testItem.getOfferAmount() == 2000);
	}
	@Test
	@DisplayName("Testing boundaries for the offer")
	public void boundaryTest() throws RemoteException {
		observableTestItem.userSaleStrategy(-1,"TestName1");
		assertTrue(testItem.getOfferAmount() == 0);

		observableTestItem.userSaleStrategy(Integer.MAX_VALUE,"TestName2");
		assertTrue(testItem.getOfferAmount() == Integer.MAX_VALUE);
	}



}
