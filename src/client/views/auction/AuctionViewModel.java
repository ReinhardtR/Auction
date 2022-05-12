package client.views.auction;

import client.model.ItemCalculations;
import client.model.ObservableItem;
import client.model.ObservableItemList;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
	private final IntegerProperty currentHighestBid;
	private final StringProperty timeLeft;
	private final ObservableItem item;

	public AuctionViewModel(ObservableItemList observableItemList) {
		itemText = new SimpleStringProperty();
		currentHighestBid = new SimpleIntegerProperty();
		timeLeft = new SimpleStringProperty();

		item = observableItemList.getItem("123");
		item.addListener(item.getItemID(), this);

		runTimeSimulation(item.getEndDateTime());
	}

	public void bidOnItem(int offer) {
		if (ItemCalculations.isNewBidHigher(offer, item)) {
			item.userSaleStrategy(offer, "Reinhardt");
		}
	}

	public StringProperty propertyItemLabel() {
		return itemText;
	}

	public IntegerProperty propertyCurrentBid() {
		return currentHighestBid;
	}

	public StringProperty propertyTimeLeft() {
		return timeLeft;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		System.out.println("change!");
		Platform.runLater(() -> {
			currentHighestBid.setValue(item.getOfferAmount());
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
