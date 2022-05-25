package server.persistence.item.mutation.timers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import server.model.item.ItemImpl;
import server.model.item.sale_strategy.AuctionStrategy;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuctionCountDownTest {


	private static final int secondsIntoFutureTest = 5;
	private static ItemImpl fakeItemForFutureTest;
	private static ItemImpl fakeItemForPastTest;
	//Used by listener
	int counter = 1;
	private boolean listenerMethodCalled = false;

	@BeforeAll
	static void setUp() {
		try {
			fakeItemForFutureTest = new ItemImpl("1", "title", "description", "tags", new AuctionStrategy(0, "testMan", LocalDateTime.now().plusSeconds(secondsIntoFutureTest)));
			fakeItemForPastTest = new ItemImpl("2", "title", "description", "tags", new AuctionStrategy(0, "testman2", LocalDateTime.now().minusHours(24)));

		} catch (RemoteException e) {
			e.printStackTrace();

		}
	}

	@Test
	void auctionTimerTester() {
		try {
			Thread Auction5SecondsIntoFuture = new Thread(new AuctionCountDown(fakeItemForFutureTest.getItemID(), fakeItemForFutureTest.getEndTimestamp(),
							LocalDateTime.now(), this::listenerTestMethod));

			Auction5SecondsIntoFuture.start();
			Thread.sleep(secondsIntoFutureTest * 1100);


			assertTrue(listenerMethodCalled);


			// Nu laver jeg et item som allerede er udløbet,
			// den burde kalde "listener" metoden med det samme. Sleep er sat på for at være sikker på at den nå at fire sin propertychange
			listenerMethodCalled = false;


			Thread AuctionTimerAlreadyRanOut = new Thread(new AuctionCountDown(fakeItemForPastTest.getItemID(), fakeItemForPastTest.getEndTimestamp(),
							LocalDateTime.now(), this::listenerTestMethod));

			AuctionTimerAlreadyRanOut.start();

			Thread.sleep(1000);
			assertTrue(listenerMethodCalled);


		} catch (RemoteException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void listenerTestMethod(PropertyChangeEvent propertyChangeEvent) {

		if (counter == 1) {
			try {
				assertEquals(fakeItemForFutureTest.getItemID(), propertyChangeEvent.getNewValue());
				counter++;
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} else if (counter == 2) {
			try {
				assertEquals(fakeItemForPastTest.getItemID(), propertyChangeEvent.getNewValue());
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		listenerMethodCalled = true;

	}


}