import client.model.ObservableItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.model.item.Item;
import server.model.item.ItemImpl;
import server.model.item.ItemProxy;
import server.model.item.SaleStrategy.AuctionStrategy;
import shared.network.model.GenerelItems;

import java.rmi.RemoteException;
import java.time.DateTimeException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


public class ItemTest {
	private Item testItem;
	private Item testItemProxy;
	private Item brokenTestItem;
	private GenerelItems observableTestItem;
	private GenerelItems brokenObservableItem;

	@BeforeEach
	void createTestItem() throws RemoteException {
		testItem = new ItemImpl("123", LocalDateTime.of(2022, 5, 16, 11, 20), new AuctionStrategy(0));
		testItemProxy = new ItemProxy(testItem);
		observableTestItem = new ObservableItem(null, testItemProxy);

		brokenTestItem = new ItemImpl("123412414fqwfd123", LocalDateTime.of(200002, 12, 31, 23, 59), null);
		brokenObservableItem = new ObservableItem(null, brokenTestItem);
	}

	@Test
	@DisplayName("Testing currentBid increases for every new offer")
	public void auctionBiddingUpTest() throws RemoteException {
		assertTrue(testItem.getOfferAmount() == 0);

		observableTestItem.userSaleStrategy(20, "TestName1");
		assertTrue(testItem.getOfferAmount() == 20);

		observableTestItem.userSaleStrategy(2000, "TestName2");
		assertTrue(testItem.getOfferAmount() == 2000);

		observableTestItem.userSaleStrategy(1000, "TestName1");
		assertTrue(testItem.getOfferAmount() == 2000);
	}

	@Test
	@DisplayName("Testing boundaries for the offer")
	public void boundaryTest() throws RemoteException {
		observableTestItem.userSaleStrategy(-20, "TestName1");
		assertTrue(testItem.getOfferAmount() == 0);

		observableTestItem.userSaleStrategy(Integer.MAX_VALUE, "TestName2");
		assertTrue(testItem.getOfferAmount() == Integer.MAX_VALUE);
	}

	@Test
	@DisplayName("Testing functionality of broken item")
	public void brokenStrategy() throws RemoteException {
		//Ingen strategy er sat vi forventer der bliver throwet en null pointer
		assertThrows(NullPointerException.class, () -> brokenObservableItem.userSaleStrategy(-123, ""));
	}

	@Test
	@DisplayName("Testing af tid")
	public void testEndTime() throws RemoteException {
		assertEquals(brokenObservableItem.getEndTimestamp(), LocalDateTime.of(200002, 12, 31, 23, 59));
	}

	@Test
	@DisplayName("Creation af forkerte tidspunkter")
	public void testBadCreation() {
		assertThrows(DateTimeException.class, () -> new ItemImpl("1", LocalDateTime.of(-1141341134, 20000, 1, 1, 1), null));
		assertThrows(DateTimeException.class, () -> new ItemImpl("1", LocalDateTime.of(1, 20000, 1, 1, 1), null));
		assertThrows(DateTimeException.class, () -> new ItemImpl("1", LocalDateTime.of(1, 1, 20000, 1, 1), null));
		assertThrows(DateTimeException.class, () -> new ItemImpl("1", LocalDateTime.of(1, 1, 1, 20000, 1), null));
		assertThrows(DateTimeException.class, () -> new ItemImpl("1", LocalDateTime.of(1, 1, 1, 1, 20000), null));
	}

	
}
