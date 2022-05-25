package client.views.auction;

import client.core.ViewHandler;
import client.model.ItemCalculations;
import client.model.ObservableItem;
import client.model.User;
import client.utils.SystemNotifcation;
import client.utils.ViewEnum;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import shared.EventType;
import shared.utils.TimedTask;

import java.beans.PropertyChangeEvent;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;

public class AuctionViewModelImpl implements AuctionViewModel {
	private final User customer;
	private final ObservableItem item;

	private final StringProperty itemName;
	private final BooleanProperty isSold;
	private final DoubleProperty currentHighestBid;
	private final StringProperty timeLeft;
	private final StringProperty eventText;
	private final StringProperty seller;
	private final StringProperty description;
	private final StringProperty tags;
	private final StringProperty highestBidder;
	private final ObjectProperty<Paint> eventColor;

	public AuctionViewModelImpl(User customer, ObservableItem item) {
		this.customer = customer;
		this.item = item;

		item.addListener(EventType.NEW_BID.toString(), this::onNewBid);
		item.addListener(EventType.ITEM_SOLD.toString(), this::onItemSold);

		itemName = new SimpleStringProperty();
		isSold = new SimpleBooleanProperty();
		currentHighestBid = new SimpleDoubleProperty();
		timeLeft = new SimpleStringProperty();
		eventText = new SimpleStringProperty();
		seller = new SimpleStringProperty();
		description = new SimpleStringProperty();
		tags = new SimpleStringProperty();
		highestBidder = new SimpleStringProperty();
		eventColor = new SimpleObjectProperty<>();

		itemName.setValue(item.getTitle());
		currentHighestBid.setValue(item.getOfferAmount());
		seller.setValue(item.getSalesmanUsername());
		description.setValue(item.getDescription());
		highestBidder.setValue(item.getBuyerUsername());
		tags.setValue(item.getTags());

		runTimeSimulation(item.getEndTimestamp());
	}

	@Override
	public void bidOnItem(String offerInputText) {
		try {
			double offerAmount = Double.parseDouble(offerInputText);

			if (ItemCalculations.isNewBidHigher(offerAmount, item)) {
				eventText.setValue(null);
				customer.makeOfferOnItem(offerAmount, item);
				highestBidder.setValue(item.getBuyerUsername());
				eventColor.setValue(Color.GREEN);
			} else {
				eventText.setValue("You need to bid higher than the current bid.");
				eventColor.setValue(Color.RED);
			}
		} catch (NumberFormatException | NullPointerException e) {
			eventText.setValue("Please type a valid number as your bid.");
			eventColor.setValue(Color.RED);
		}
	}

	@Override
	public StringProperty propertyItemName() {
		return itemName;
	}

	@Override
	public DoubleProperty propertyCurrentBid() {
		return currentHighestBid;
	}

	@Override
	public StringProperty propertyTimeLeft() {
		return timeLeft;
	}

	@Override
	public StringProperty propertyEventText() {
		return eventText;
	}

	@Override
	public BooleanProperty propertyIsSold() {
		return isSold;
	}

	@Override
	public StringProperty propertySeller() {
		return seller;
	}

	@Override
	public StringProperty propertyHighestBidder() {
		return highestBidder;
	}

	@Override
	public StringProperty propertyDescription() {
		return description;
	}

	@Override
	public StringProperty propertyTags() {
		return tags;
	}

	@Override
	public ObjectProperty<Paint> propertyEventColor() {
		return eventColor;
	}

	private void onItemSold(PropertyChangeEvent event) {
		Platform.runLater(() -> {
			isSold.setValue(true);
		});
	}

	private void onNewBid(PropertyChangeEvent event) {
		Platform.runLater(() -> {
			String itemID = (String) event.getOldValue();
			double offerAmount = (double) event.getNewValue();

			currentHighestBid.setValue(offerAmount);

			// Display notification
			String caption = "New bid on: " + itemID;
			String message = "A bid of: " + offerAmount + ", has been placed on item: " + itemID + ".";
			SystemNotifcation.getInstance().send(caption, message);
		});
	}

	@Override
	public void returnToItemListView() {
		ViewHandler.getInstance().openView(ViewEnum.ItemList.toString());
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
