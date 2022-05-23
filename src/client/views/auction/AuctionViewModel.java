package client.views.auction;

import client.model.ItemCalculations;
import client.model.ObservableItem;
import client.utils.SystemNotifcation;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import shared.EventType;
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
	private final StringProperty errorText;
	private final ObservableItem item;

	public AuctionViewModel(ObservableItem item) {
		itemText = new SimpleStringProperty();
		currentHighestBid = new SimpleDoubleProperty();
		timeLeft = new SimpleStringProperty();
		errorText = new SimpleStringProperty();

		this.item = item;
		item.addListener(EventType.NEW_BID.toString(), this);

		itemText.setValue(item.getItemID());
		currentHighestBid.setValue(item.getOfferAmount());

		runTimeSimulation(item.getEndTimestamp());
	}

	public void bidOnItem(String offerInputText) {
		try {
			double offer = Double.parseDouble(offerInputText);

			if (ItemCalculations.isNewBidHigher(offer, item)) {
				errorText.setValue(null);
				item.userSaleStrategy(offer, "username");
			} else {
				errorText.setValue("You need to bid higher than the current bid.");
			}
		} catch (NumberFormatException | NullPointerException e) {
			errorText.setValue("Please type a valid number as your bid.");
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

	public StringProperty propertyErrorText() {
		return errorText;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		Platform.runLater(() -> {
			double offerAmount = item.getOfferAmount();
			String itemID = item.getItemID();

			currentHighestBid.setValue(offerAmount);

			// Display system notification
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
