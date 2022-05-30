package client.views.sale;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Paint;
import shared.SaleStrategyType;

import java.time.LocalDate;

public interface SaleViewModel {
	ObjectProperty<LocalDate> endDateProperty();

	StringProperty titleTextProperty();

	StringProperty descriptionTextProperty();

	StringProperty tagsTextProperty();

	StringProperty priceBidTextProperty();

	void returnToItemList();

	void setItemUpForSale(SaleStrategyType saleType);

	StringProperty eventLabelTextProperty();

	StringProperty endTimeTextProperty();

	ObjectProperty<Paint> eventLabelPaintProperty();
}
