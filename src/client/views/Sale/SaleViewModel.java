package client.views.Sale;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;

public interface SaleViewModel {
	StringProperty titleTextProperty();

	StringProperty descriptionTextProperty();

	StringProperty tagsTextProperty();

	DoubleProperty priceBidTextProperty();

	void returnToItemList();

	void setItemUpForSale();
}
