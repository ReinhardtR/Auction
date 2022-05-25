package client.views.buyout;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;


public interface BuyoutViewModel {

	void buyItem();

	DoubleProperty propertyPrice();

	StringProperty propertyItemName();

	StringProperty propertyErrorText();

	BooleanProperty propertyIsSold();

	void returnToItemListView();

	StringProperty propertySellerUser();

	StringProperty propertyDescriptionText();

	StringProperty propertyTagsText();

	StringProperty propertyBuyerText();

}
