package client.views.buyout;

import client.core.ClientFactory;
import client.core.ViewHandler;
import client.model.ItemCalculations;
import client.model.ObservableItem;
import client.network.ClientImpl;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;

public class BuyoutViewModelImpl implements BuyoutViewModel{
	private final ObservableItem item;
	private final DoubleProperty priceProperty;
	private final StringProperty itemNameProperty;
	private final StringProperty errorProperty;


	public BuyoutViewModelImpl(ObservableItem item) {
		this.item = item;
		item.addListener(item.getItemID(), this);

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
	public void returnToItemListView() {
		try {
			item.getUpdateBroadcaster().unregisterClient((ClientImpl)ClientFactory.getInstance().getClient());
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
		ViewHandler.getInstance().openItemListView();
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		// TODO: sold property?
	}
}
