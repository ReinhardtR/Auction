package client.views.buyout;

import client.core.ClientFactory;
import client.core.ViewHandler;
import client.model.ItemCalculations;
import client.model.ObservableItem;
import client.network.ClientImpl;
import client.utils.SystemNotifcation;
import javafx.beans.property.*;
import shared.EventType;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;

public class BuyoutViewModelImpl implements BuyoutViewModel{
	private final ObservableItem item;

	// Brug samme naming convention i begge viewmodels
	private final BooleanProperty isSoldProperty;
	private final DoubleProperty priceProperty;
	private final StringProperty itemNameProperty;
	private final StringProperty errorProperty;


	public BuyoutViewModelImpl(ObservableItem item) {
		this.item = item;
		item.addListener(EventType.ITEM_SOLD + item.getItemID(), this::onItemSold);

		isSoldProperty = new SimpleBooleanProperty();
		priceProperty = new SimpleDoubleProperty();
		itemNameProperty = new SimpleStringProperty();
		errorProperty = new SimpleStringProperty();

		itemNameProperty.setValue(item.getItemID());
		priceProperty.setValue(item.getOfferAmount());
	}

	@Override
	public void onBuy(double amount, String username) {
		System.out.println("Buying");
		if(ItemCalculations.isItemSold(item))
		{
			errorProperty.setValue("Item is already sold!");
		}
		else
		{
			item.userSaleStrategy(amount, username);
		}
	}

	@Override
	public DoubleProperty getPriceProperty() {
		return priceProperty;
	}

	@Override
	public StringProperty getItemNameProperty() {
		return itemNameProperty;
	}

	@Override
	public StringProperty getErrorProperty() {
		return errorProperty;
	}

	@Override
	public BooleanProperty getIsSoldProperty() {
		return isSoldProperty;
	}

	private void onItemSold(PropertyChangeEvent event) {
		isSoldProperty.setValue(true);

		String caption = "Item sold: " + item.getItemID();
		String message = "The item: " + item.getItemID() + ", that you were watching has been sold.";
		SystemNotifcation.getInstance().send(caption, message);
	}

	@Override
	public void returnToItemListView() {
		try {
			item.getUpdateBroadcaster().unregisterClient((ClientImpl)ClientFactory.getInstance().getClient());
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
		ViewHandler.getInstance().openItemListView();
	}
}
