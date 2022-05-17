package client.views.buyout;

import client.model.ObservableItem;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class BuyoutViewModel implements PropertyChangeListener {
	private final ObservableItem item;
	private final DoubleProperty priceProperty;
	private final StringProperty itemNameProperty;

	public BuyoutViewModel(ObservableItem item) {
		this.item = item;
		item.addListener(item.getItemID(), this);

		priceProperty = new SimpleDoubleProperty();
		itemNameProperty = new SimpleStringProperty();

		itemNameProperty.setValue(item.getItemID());
		priceProperty.setValue(item.getOfferAmount());
	}

	public void onBuy(double amount, String username) {
		System.out.println("Buying");
		item.userSaleStrategy(amount, username);
	}

	public DoubleProperty getPriceProperty() {
		return priceProperty;
	}

	public StringProperty getItemNameProperty() {
		return itemNameProperty;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		// TODO: sold property?
	}
}
