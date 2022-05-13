import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import server.model.item.Item;
import server.model.item.ItemImpl;
import server.model.item.SaleStrategy.AuctionStrategy;

import java.rmi.RemoteException;
import java.time.LocalDateTime;


public class ItemTest {
	private Item testItem;
	@BeforeEach
	void createTestItem() throws RemoteException {
			testItem = new ItemImpl("123", LocalDateTime.of(2022, 5, 16, 11, 20), new AuctionStrategy(0));
	}


	@Test
	public void simpleIdTestTest() {
		Assertions.assertTrue(testItem.getItemID());
	}
}
