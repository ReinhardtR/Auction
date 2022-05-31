package server.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.model.item.ItemImpl;
import server.model.item.sale_strategy.AuctionStrategy;
import server.service.customer.CustomerItemService;
import shared.network.model.Item;

import java.rmi.RemoteException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerItemServiceTest {
	private static Item item;

	@BeforeAll
	static void setUp() throws RemoteException {
		item = new ItemImpl("item-id", "salesman", "title", "description",
						"tags", new AuctionStrategy(10, "Lars", LocalDateTime.now().plusHours(1)));
	}

	@BeforeEach
	void tearDown() {
		CustomerItemService.getInstance().clearAllItems();
	}

	@Test
	@DisplayName("Getting a CustomerItemService instance with Singleton pattern")
	void getInstance() {
		assertNotNull(CustomerItemService.getInstance(), "Getting the first initialization of the Class instance returns null");
		assertNotNull(CustomerItemService.getInstance(), "Getting an existing instance of the Class returns null");
	}

	@Test
	@DisplayName("Getting items from the CustomerItemService")
	void getItem() throws RemoteException {
		//0
		assertNull(CustomerItemService.getInstance().getItem(item.getItemID()), "Getting a non-existing item should be null");

		// 1
		CustomerItemService.getInstance().addItem(item);
		assertEquals(item, CustomerItemService.getInstance().getItem(item.getItemID()), "The item received should equal the previously added item");

		// E
		assertNull(CustomerItemService.getInstance().getItem(null), "Should return null");
	}

	@Test
	@DisplayName("Adding items to the CustomerItemService")
	void addItem() throws RemoteException {
		// 1
		CustomerItemService.getInstance().addItem(item);
		assertEquals(item, CustomerItemService.getInstance().getItem(item.getItemID()), "The item received should equal the previously added item");


		// E
		assertThrows(NullPointerException.class, () -> CustomerItemService.getInstance().addItem(null), "The method should throw a NullPointerException if the item is null");
		assertEquals(1, CustomerItemService.getInstance().getAllStoredItems().size(), "Ensure that the null item has not been added to the stored items");
	}

	@Test
	@DisplayName("Getting all items from the CustomerItemService")
	void getAllItems() throws RemoteException {
		// 0
		assertEquals(0, CustomerItemService.getInstance().getAllStoredItems().size(), "The list should be empty");

		// 1
		CustomerItemService.getInstance().addItem(item);
		assertEquals(1, CustomerItemService.getInstance().getAllStoredItems().size(), "The list should contain 1 item, which was just added");

		// Many
		CustomerItemService.getInstance().addItem(item);
		CustomerItemService.getInstance().addItem(item);
		CustomerItemService.getInstance().addItem(item);
		CustomerItemService.getInstance().addItem(item);
		assertEquals(5, CustomerItemService.getInstance().getAllStoredItems().size(), "The list should contain 5 item, which was just added");

		// B
		CustomerItemService.getInstance().clearAllItems();
		assertEquals(0, CustomerItemService.getInstance().getAllStoredItems().size(), "The list should be empty, as the newly added item has been bought and removed");
	}
}
