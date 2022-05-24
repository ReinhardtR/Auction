package client.views.Sale;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

import java.time.chrono.Chronology;

public interface SaleViewModel {
	StringProperty titleTextProperty();

	StringProperty descriptionTextProperty();

	StringProperty tagsTextProperty();

	DoubleProperty priceBidTextProperty();

	void returnToItemList();

	void setItemUpForSale();

	StringProperty errorLabelProperty();

	StringProperty endTimeTextProperty();

	ObservableValue<? extends Chronology> endDateChronologyProperty();
}
