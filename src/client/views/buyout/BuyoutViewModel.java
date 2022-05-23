package client.views.buyout;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public interface BuyoutViewModel {

	void onBuy(double amount, String username);

	// brug samme naming convention i begge viewmodels (kan godt lide den i auction)
	DoubleProperty getPriceProperty();

	StringProperty getItemNameProperty();

	StringProperty getErrorProperty();

	BooleanProperty getIsSoldProperty();

	void returnToItemListView();
}
