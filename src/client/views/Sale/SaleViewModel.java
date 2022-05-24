package client.views.Sale;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import shared.SaleStrategyType;

import java.time.chrono.Chronology;

public interface SaleViewModel {
	StringProperty titleTextProperty();

	StringProperty descriptionTextProperty();

	StringProperty tagsTextProperty();

	StringProperty priceBidTextProperty();

	void returnToItemList();

	void setItemUpForSale(SaleStrategyType saleType);

	StringProperty errorLabelProperty();

	StringProperty endTimeTextProperty();

	ObservableValue<? extends Chronology> endDateChronologyProperty();
}
