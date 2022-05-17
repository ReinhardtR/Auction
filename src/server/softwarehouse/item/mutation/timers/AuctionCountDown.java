package server.softwarehouse.item.mutation.timers;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;

public class AuctionCountDown implements Runnable {
	private final String itemID;
	private final Temporal endTime;
	private final Temporal localTimeIn1Hour;
	private final PropertyChangeSupport support;


	public AuctionCountDown(String itemID, Temporal endTime, LocalDateTime localTimeIn1Hour, PropertyChangeListener listener) {
		this.itemID = itemID;
		this.endTime = endTime;
		this.localTimeIn1Hour = localTimeIn1Hour;
		support = new PropertyChangeSupport(this);
		support.addPropertyChangeListener(listener);
	}


	@Override
	public void run() {
		Duration duration = Duration.between(endTime, localTimeIn1Hour);


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
