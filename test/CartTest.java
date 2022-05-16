import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.model.item.Cart;
import server.model.item.Item;
import server.model.item.ItemImpl;
import server.model.item.SaleStrategy.AuctionStrategy;

import java.rmi.RemoteException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class CartTest {
	private static Item item;
	private static Item brokenItem;

	@BeforeAll
	static void setUp() throws RemoteException {
		item = new ItemImpl("item-id", LocalDateTime.now(), new AuctionStrategy(0));
	}

	@BeforeEach
	void tearDown() {
		Cart.getInstance().clearAllItems();
	}

	@Test
	@DisplayName("Getting a Cart instance with Singleton pattern")
	void getInstance() {
		assertNotNull(Cart.getInstance(), "Getting the first initialization of the Class instance returns null");
		assertNotNull(Cart.getInstance(), "Getting an existing instance of the Class returns null");
	}

	@Test
	@DisplayName("Getting items from the cart")
	void getItem() throws RemoteException {
		assertNull(Cart.getInstance().getItem(item.getItemID()), "Getting a non-existing item should be null");

		// Sunny
		Cart.getInstance().addItem(item);
		assertEquals(item, Cart.getInstance().getItem(item.getItemID()), "The item received should equal the previously added item");

		// Rainy
		assertNull(Cart.getInstance().getItem(null), "Should return null");
	}

	@Test
	@DisplayName("Adding items to the cart")
	void addItem() throws RemoteException {
		// Sunny
		Cart.getInstance().addItem(item);
		assertEquals(item, Cart.getInstance().getItem(item.getItemID()), "The item received should equal the previously added item");

		// Rainy
		assertThrows(NullPointerException.class, () -> Cart.getInstance().addItem(null), "The method should throw a NullPointerException if the item is null");
		assertEquals(1, Cart.getInstance().returnAllItemsInCart().size(), "Ensure that the null item has not been added to the cart");
	}

	@Test
	@DisplayName("Getting all items from the cart")
	void getAllItems() throws RemoteException {
		assertEquals(0, Cart.getInstance().returnAllItemsInCart().size(), "The list should be empty");

		Cart.getInstance().addItem(item);
		assertEquals(1, Cart.getInstance().returnAllItemsInCart().size(), "The list should contain 1 item, which was just added");

		Cart.getInstance().itemBought(item);
		assertEquals(0, Cart.getInstance().returnAllItemsInCart().size(), "The list should be empty, as the newly added item has been bought and removed");
	}
}
