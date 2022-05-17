package server.softwarehouseacces.item.transactions.timers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import server.model.item.Item;
import server.model.item.ItemImpl;
import server.model.item.SaleStrategy.AuctionStrategy;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AuctionCountDownTest {


	private static ItemImpl fakeItemForTest;
	private boolean listenerMethodCalled = false;
	private static final int secondsIntoFutureTest = 5;
	@BeforeAll
static void setUp()
{
	try {
		fakeItemForTest = new ItemImpl("1",new AuctionStrategy(0,"testMan", LocalDateTime.now().plusSeconds(secondsIntoFutureTest)));
	} catch (RemoteException e) {
		e.printStackTrace();
	}
}


	@Test
	void auctionTimerTester()
	{
		try {
			Thread AuctionCountDowntest = new Thread(new AuctionCountDown(fakeItemForTest.getItemID(),fakeItemForTest.getEndTimestamp(),
																								LocalDateTime.now().plusHours(1),this::listenerTestMethod));

			AuctionCountDowntest.start();
			Thread.sleep(secondsIntoFutureTest+5);

			assertTrue(listenerMethodCalled);


		} catch (RemoteException | InterruptedException e) {
			e.printStackTrace();
		}

	}

	private void listenerTestMethod(PropertyChangeEvent propertyChangeEvent) {
		listenerMethodCalled = true;
	}

}