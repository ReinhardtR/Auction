package client.views.auction;

import client.core.ClientFactory;
import client.core.ViewHandler;
import client.model.ItemCalculations;
import client.model.ObservableItem;
import client.network.ClientImpl;
import client.utils.SystemNotifcation;
import javafx.application.Platform;
import javafx.beans.property.*;
import shared.EventType;
import shared.utils.TimedTask;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;

public class AuctionViewModelImpl implements AuctionViewModel {
	private final StringProperty itemText;
	private final BooleanProperty isSold;
	private final DoubleProperty currentHighestBid;
	private final StringProperty timeLeft;
	private final StringProperty errorText;
	private final ObservableItem item;

	public AuctionViewModelImpl(ObservableItem item) {
		itemText = new SimpleStringProperty();
		isSold = new SimpleBooleanProperty();
		currentHighestBid = new SimpleDoubleProperty();
		timeLeft = new SimpleStringProperty();
		errorText = new SimpleStringProperty();

		this.item = item;
		item.addListener(EventType.NEW_BID + item.getItemID(), this::onNewBid);
		item.addListener(EventType.ITEM_SOLD + item.getItemID(), this::onItemSold);

		itemText.setValue(item.getItemID());
		currentHighestBid.setValue(item.getOfferAmount());

		runTimeSimulation(item.getEndTimestamp());
	}

	@Override
	public void bidOnItem(String offerInputText) {
		try {
			double offer = Double.parseDouble(offerInputText);

			if (ItemCalculations.isNewBidHigher(offer, item)) {
				errorText.setValue(null);
				item.userSaleStrategy(offer, "Reinhardt");
			} else {
				errorText.setValue("You need to bid higher than the current bid.");
			}
		} catch (NumberFormatException | NullPointerException e) {
			errorText.setValue("Please type a valid number as your bid.");
		}
	}
	@Override
	public StringProperty propertyItemLabel() {
		return itemText;
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
	public StringProperty propertyErrorText() {
		return errorText;
	}

	@Override
	public BooleanProperty propertyIsSold() {
		return isSold;
	}

	private void onItemSold(PropertyChangeEvent event) {
		Platform.runLater(() -> {
			isSold.setValue(true);
		});
	}

	private void onNewBid(PropertyChangeEvent event) {
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

	@Override
	public void returnToItemListView() {
			try {
				item.getUpdateBroadcaster().unregisterClient((ClientImpl) ClientFactory.getInstance().getClient());
			} catch (RemoteException e) {
				throw new RuntimeException(e);
			}
			ViewHandler.getInstance().openItemListView();
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