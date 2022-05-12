package client.views.buyout;

import client.model.ObservableItem;
import client.model.ObservableItemList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class BuyoutViewModel implements PropertyChangeListener {
	private final ObservableItem item;
	private IntegerProperty priceProperty;
	private StringProperty itemNameProperty;

	public BuyoutViewModel(ObservableItemList itemList) {

		item = itemList.getItem(itemList.getIDForView());
		item.addListener(item.getItemID(), this);

		priceProperty = new SimpleIntegerProperty();
		itemNameProperty = new SimpleStringProperty();

		itemNameProperty.setValue(item.getItemID());
	}

	public void onBuy(int amount, String username) {
		item.userSaleStrategy(amount, username);
	}

	public IntegerProperty getPriceProperty() {
		return priceProperty;
	}

	public StringProperty getItemNameProperty() {
		return itemNameProperty;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

	}
}
