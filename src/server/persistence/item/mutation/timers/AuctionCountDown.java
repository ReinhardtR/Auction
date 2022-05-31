package server.persistence.item.mutation.timers;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;

public class AuctionCountDown implements Runnable {
	private final String itemID;
	private final Temporal endTime;
	private final Temporal localTimeNow;
	private final PropertyChangeSupport support;


	public AuctionCountDown(String itemID, Temporal endTime, LocalDateTime now, PropertyChangeListener listener) {
		this.itemID = itemID;
		this.endTime = endTime;
		localTimeNow = now;
		support = new PropertyChangeSupport(this);
		support.addPropertyChangeListener(listener);
	}


	@Override
	public void run() {
		Duration duration = Duration.between(localTimeNow, endTime);

		if (!(duration.isNegative())) {
			try {
				Thread.sleep(duration.toMillis());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		support.firePropertyChange("Time is up on item " + itemID, null, itemID);
	}
}
