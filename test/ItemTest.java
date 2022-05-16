import client.model.ObservableItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.model.item.Item;
import server.model.item.ItemImpl;
import server.model.item.SaleStrategy.AuctionStrategy;
import shared.SaleStrategyType;
import shared.network.model.GenerelItems;

import java.rmi.RemoteException;
import java.time.DateTimeException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


public class ItemTest {
	private Item testItem;
	private Item brokenTestItem;
	private GenerelItems observableTestItem;
	private GenerelItems brokenObservableItem;

	@BeforeEach
	void createTestItem() throws RemoteException {
		testItem = new ItemImpl("123", new AuctionStrategy(0,"tis", LocalDateTime.of(2022, 5, 16, 11, 20)));
		observableTestItem = new ObservableItem(null, testItem);

		brokenTestItem = new ItemImpl(null, null);
		brokenObservableItem = new ObservableItem(null, brokenTestItem);
	}

	//UserSale Strategy ZOMBIES
	@Test
	@DisplayName("Testing currentBid increases for every new offer")
	public void auctionBiddingUpTest() throws RemoteException {
		assertEquals(0, testItem.getOfferAmount());

		observableTestItem.userSaleStrategy(20, "TestName1");
		assertEquals(20, testItem.getOfferAmount());

		observableTestItem.userSaleStrategy(2000, "TestName2");
		assertEquals(2000, testItem.getOfferAmount());

		observableTestItem.userSaleStrategy(1000, "TestName1");
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
	@DisplayName("Testing functionality of broken item")
	public void brokenStrategy() {
		//Ingen strategy er sat vi forventer der bliver throwet en null pointer
		assertThrows(NullPointerException.class, () -> brokenObservableItem.userSaleStrategy(-123, ""));
	}

	@Test
	@DisplayName("Testing itemID when is null")
	public void itemIDTest() throws RemoteException {
		assertNull(brokenObservableItem.getItemID());
	}

	@Test
	@DisplayName("Strategy type test")
	public void strategyTypeTest() throws RemoteException {
		assertEquals(observableTestItem.getStrategyType(), SaleStrategyType.AUCTION);
	}

	@Test
	@DisplayName("Null boundary")
	public void strategyBrokenTest() {
		assertThrows(NullPointerException.class, () -> brokenObservableItem.getStrategyType());
	}


	@Test
	@DisplayName("Testing af tid")
	public void testEndTime() throws RemoteException {
		assertEquals(brokenObservableItem.getEndTimestamp(), LocalDateTime.of(200002, 12, 31, 23, 59));
	}

	@Test
	@DisplayName("Creation af forkerte tidspunkter")
	public void testBadCreation() {
		/*
		//Year
		assertThrows(DateTimeException.class, () -> new ItemImpl("1", LocalDateTime.of(-1141341134, 20000, 1, 1, 1), null));

		//Month
		assertThrows(DateTimeException.class, () -> new ItemImpl("1", LocalDateTime.of(1, 20000, 1, 1, 1), null));

		//Day
		assertThrows(DateTimeException.class, () -> new ItemImpl("1", LocalDateTime.of(1, 1, 20000, 1, 1), null));

		//Hour
		assertThrows(DateTimeException.class, () -> new ItemImpl("1", LocalDateTime.of(1, 1, 1, 20000, 1), null));

		//Day
		assertThrows(DateTimeException.class, () -> new ItemImpl("1", LocalDateTime.of(1, 1, 1, 1, 20000), null));

		 */
	}


}
