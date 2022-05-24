package client.views.buyout;

import client.core.ViewHandler;
import client.model.ObservableItem;
import client.model.User;
import client.utils.SystemNotifcation;
import client.utils.ViewEnum;
import javafx.beans.property.*;
import shared.EventType;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;

public class BuyoutViewModelImpl implements BuyoutViewModel {
	private final User customer;
	private final ObservableItem item;

	private final StringProperty itemName;
	private final BooleanProperty isSold;
	private final DoubleProperty price;
	private final StringProperty errorText;

	public BuyoutViewModelImpl(User customer, ObservableItem item) {
		this.customer = customer;
		this.item = item;

		item.addListener(EventType.ITEM_SOLD.toString(), this::onItemSold);

		itemName = new SimpleStringProperty();
		isSold = new SimpleBooleanProperty();
		price = new SimpleDoubleProperty();
		errorText = new SimpleStringProperty();

		itemName.setValue(item.getItemID());
		price.setValue(item.getOfferAmount());
	}

	@Override
	public void buyItem() {
		try {
			if (item.getIsSold()) {
				errorText.setValue("Item is already sold!");
			} else {
				customer.makeOfferOnItem(price.getValue(), item);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
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
		isSold.setValue(true);

		String caption = "Item sold: " + item.getItemID();
		String message = "The item: " + item.getItemID() + ", that you were watching has been sold.";
		SystemNotifcation.getInstance().send(caption, message);
	}

	@Override
	public void returnToItemListView() {
		ViewHandler.getInstance().openView(ViewEnum.ItemList.toString());
	}
}
