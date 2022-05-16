package server.softwarehouseacces.item.transactions.timers;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;

public class AuctionCountDown implements Runnable {
	private final int itemID;
	private final Timestamp endTime;
	private final LocalDateTime localTimeIn1Hour;
	private final PropertyChangeSupport support;




	public AuctionCountDown(int itemID, Timestamp endTime, LocalDateTime localTimeIn1Hour, PropertyChangeListener listener) {
		this.itemID = itemID;
		this.endTime = endTime;
		this.localTimeIn1Hour = localTimeIn1Hour;
		support = new PropertyChangeSupport(this);
		support.addPropertyChangeListener(listener);
	}

	@Override
	public void run() {
		Duration duration = Duration.between(endTime.toLocalDateTime(),  localTimeIn1Hour);

		System.out.println("itemID = " + itemID + " | " + "endtime = " + endTime);             //Skal fjernes senere

		System.out.println(!duration.isNegative());
		if (!(duration.isNegative()))
		{
			System.out.println("Inside of if");
			try {
			Thread.sleep(duration.toMillis());
			} catch (InterruptedException e) {
			e.printStackTrace();
			}
		}

		System.out.println("FIRING PROPERTY");
		support.firePropertyChange("Time is up on item " + itemID, null, itemID);

	}
}
