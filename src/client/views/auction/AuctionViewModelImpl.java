package client.views.auction;

import client.core.ViewHandler;
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
	private final StringProperty salesmanUsername;
	private final StringProperty description;
	private final StringProperty tags;
	private final StringProperty highestBidder;
	private final ObjectProperty<Paint> eventColor;

	public AuctionViewModelImpl(User customer, ObservableItem item) {
		this.customer = customer;
		this.item = item;

		item.addListener(EventType.NEW_BID.toString(), this::onNewBid);
		item.addListener(EventType.ITEM_SOLD.toString(), this::onItemSold);

		itemName = new SimpleStringProperty(item.getTitle());
		currentHighestBid = new SimpleDoubleProperty(item.getOfferAmount());
		isSold = new SimpleBooleanProperty(item.getIsSold());
		salesmanUsername = new SimpleStringProperty(item.getSalesmanUsername());
		description = new SimpleStringProperty(item.getDescription());
		tags = new SimpleStringProperty(item.getTags());
		highestBidder = new SimpleStringProperty(item.getBuyerUsername());

		timeLeft = new SimpleStringProperty();
		eventText = new SimpleStringProperty();
		eventColor = new SimpleObjectProperty<>();

		runTimeSimulation(item.getEndTimestamp());
	}

	@Override
	public void bidOnItem(String offerInputText) {
		try {
			double offerAmount = Double.parseDouble(offerInputText);

			if (offerAmount > item.getOfferAmount()) {
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
	public StringProperty propertySalesmanUsername() {
		return salesmanUsername;
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
			String itemID = item.getItemID();
			double offerAmount = item.getOfferAmount();
			String highestBidderName = item.getBuyerUsername();

			currentHighestBid.setValue(offerAmount);
			highestBidder.setValue(highestBidderName);

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
		System.out.println(item.getBuyerUsername());
		TimedTask.runTask((timer) -> {
			Duration currentDurationBetween = Duration.between(LocalDateTime.now(), endDateTime);

			if (currentDurationBetween.isNegative()) {
				Platform.runLater(() -> {
					isSold.setValue(true);
					timeLeft.setValue("SOLD");
				});

				if (customer.getUsername().equals(highestBidder.getValue())) {
					SystemNotifcation.getInstance().send("You won the item!", "Congratulations, you have won the auction with a bid of: " + currentHighestBid.getValue());
				}

				timer.cancel();
				return;
			}

			String formattedTime = String.format(
							"%02d:%02d:%02d",
							currentDurationBetween.toHours(),
							currentDurationBetween.toMinutesPart(),
							currentDurationBetween.toSecondsPart()
			);

			Platform.runLater(() -> {
				timeLeft.setValue(formattedTime);
			});
		}, 1000);
	}
}
