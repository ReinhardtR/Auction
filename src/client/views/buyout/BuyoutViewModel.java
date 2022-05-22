package client.views.buyout;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public interface BuyoutViewModel extends PropertyChangeListener {


	void onBuy(double amount, String username);
	DoubleProperty getPriceProperty();
	StringProperty getItemNameProperty();
	void returnToItemListView();
}
