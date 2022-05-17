package client.views.auction;

import client.model.ItemCalculations;
import client.model.ObservableItem;
import client.utils.SystemNotifcation;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import shared.utils.TimedTask;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;

public class AuctionViewModel implements PropertyChangeListener {
	private final StringProperty itemText;
	private final DoubleProperty currentHighestBid;
	private final StringProperty timeLeft;
	private final ObservableItem item;

	public AuctionViewModel(ObservableItem item) {
		itemText = new SimpleStringProperty();
		currentHighestBid = new SimpleDoubleProperty();
		timeLeft = new SimpleStringProperty();

		this.item = item;
		item.addListener(item.getItemID(), this);

		itemText.setValue(item.getItemID());
		currentHighestBid.setValue(item.getOfferAmount());

		runTimeSimulation(item.getEndTimestamp());
	}

	public void bidOnItem(double offer) {
		if (ItemCalculations.isNewBidHigher(offer, item)) {
			item.userSaleStrategy(offer, "Reinhardt");

		}
	}


	public StringProperty propertyItemLabel() {
		return itemText;
	}

	public DoubleProperty propertyCurrentBid() {
		return currentHighestBid;
	}

	public StringProperty propertyTimeLeft() {
		return timeLeft;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		System.out.println("change!");
		Platform.runLater(() -> {
			double offerAmount = item.getOfferAmount();
			String itemID = item.getItemID();

			currentHighestBid.setValue(offerAmount);

			// Display notification
			String caption = "New bid on: " + itemID;
			String message = "A bid of: " + offerAmount + ", has been placed on item: " + itemID + ".";
			SystemNotifcation.getInstance().send(caption, message);
		});
	}

	private void runTimeSimulation(Temporal endDateTime) {
		TimedTask.runTask((timer) -> {
			Duration durationBetween = Duration.between(LocalDateTime.now(), endDateTime);

			if (durationBetween.isNegative()) {
				Platform.runLater(() -> {
					timeLeft.setValue("SOLD");
				});

				timer.cancel();
				return;
			}

			String formattedTime = String.format(
							"%02d:%02d:%02d",
							durationBetween.toHours(),
							durationBetween.toMinutesPart(),
							durationBetween.toSecondsPart()
			);

			Platform.runLater(() -> {
				timeLeft.setValue(formattedTime);
			});
		}, 1000);
	}
}
