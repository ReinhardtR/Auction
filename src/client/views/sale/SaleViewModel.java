package client.views.sale;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Paint;
import shared.SaleStrategyType;

import java.time.LocalDate;
import java.time.chrono.Chronology;

public interface SaleViewModel {
	StringProperty titleTextProperty();

	StringProperty descriptionTextProperty();

	StringProperty tagsTextProperty();

	StringProperty priceBidTextProperty();

	void returnToItemList();

	void setItemUpForSale(SaleStrategyType saleType, LocalDate date);

	StringProperty eventLabelTextProperty();

	StringProperty endTimeTextProperty();

	ObjectProperty<Paint> eventLabelPaintProperty();
}
