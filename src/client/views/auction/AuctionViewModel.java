package client.views.auction;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;

public interface AuctionViewModel {
	void bidOnItem(String offerInputText);

	void returnToItemListView();

	StringProperty propertyItemLabel();

	DoubleProperty propertyCurrentBid();

	StringProperty propertyTimeLeft();

	StringProperty propertyErrorText();

	BooleanProperty propertyIsSold();

}
