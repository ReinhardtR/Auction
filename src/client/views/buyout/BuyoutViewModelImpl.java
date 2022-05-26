package client.views.buyout;

import client.core.ViewHandler;
import client.model.ObservableItem;
import client.model.User;
import client.utils.SystemNotifcation;
import client.utils.ViewEnum;
import javafx.application.Platform;
import javafx.beans.property.*;
import shared.EventType;

import java.beans.PropertyChangeEvent;

public class BuyoutViewModelImpl implements BuyoutViewModel {
	private final User customer;
	private final ObservableItem item;

	private final StringProperty itemName;
	private final BooleanProperty isSold;
	private final DoubleProperty price;
	private final StringProperty errorText;

	private final StringProperty salesmanUsername;
	private final StringProperty description;
	private final StringProperty tags;
	private final StringProperty buyer;

	public BuyoutViewModelImpl(User customer, ObservableItem item) {
		this.customer = customer;
		this.item = item;

		item.addListener(EventType.ITEM_SOLD.toString(), this::onItemSold);

		itemName = new SimpleStringProperty(item.getTitle());
		price = new SimpleDoubleProperty(item.getOfferAmount());
		salesmanUsername = new SimpleStringProperty(item.getSalesmanUsername());
		description = new SimpleStringProperty(item.getDescription());
		tags = new SimpleStringProperty(item.getTags());
		buyer = new SimpleStringProperty(item.getBuyerUsername());
		isSold = new SimpleBooleanProperty(item.getIsSold());

		errorText = new SimpleStringProperty();
	}

	@Override
	public void buyItem() {
		customer.makeOfferOnItem(price.getValue(), item);
	}

	@Override
	public DoubleProperty propertyPrice() {
		return price;
	}

	@Override
	public StringProperty propertyItemName() {
		return itemName;
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
		System.out.println("ITEM SOLD BUYOUT");

		String caption = "Item sold: " + item.getItemID();
		String message = "The item: " + item.getItemID() + ", that you were watching has been sold.";
		SystemNotifcation.getInstance().send(caption, message);

		Platform.runLater(() -> {
			isSold.setValue(true);
			buyer.setValue(item.getBuyerUsername());
			errorText.setValue("Item is sold!");
		});
	}

	@Override
	public void returnToItemListView() {
		ViewHandler.getInstance().openView(ViewEnum.ItemList.toString());
	}

	@Override
	public StringProperty propertySellerUser() {
		return salesmanUsername;
	}

	@Override
	public StringProperty propertyDescriptionText() {
		return description;
	}

	@Override
	public StringProperty propertyTagsText() {
		return tags;
	}

	@Override
	public StringProperty propertyBuyerText() {
		return buyer;
	}

}
