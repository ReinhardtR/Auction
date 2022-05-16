import org.junit.jupiter.api.BeforeAll;
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

	@BeforeAll
	static void setUp() throws RemoteException {
		item = new ItemImpl("item-id", LocalDateTime.now(), new AuctionStrategy(0));
	}

	@Test
	@DisplayName("Getting a Cart instance with Singleton pattern")
	void getInstance() {
		assertNotNull(Cart.getInstance(), "Getting the first initialization of the Class instance returns null");
		assertNotNull(Cart.getInstance(), "Getting an existing instance of the Class returns null");
	}

	// TODO: add null scenario
	@Test
	@DisplayName("Getting items from the cart")
	void getAndAddItem() throws RemoteException {
		assertNull(Cart.getInstance().getItem(item.getItemID()), "Getting a non-existing item should be null");

		Cart.getInstance().addItem(item);

		assertEquals(item ,Cart.getInstance().getItem(item.getItemID()), "The item received should equal the previously added item");
	}
}
