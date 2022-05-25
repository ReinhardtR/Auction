package client.views.auction;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Paint;

public interface AuctionViewModel {
	void bidOnItem(String offerInputText);

	void returnToItemListView();

	StringProperty propertyItemName();

	DoubleProperty propertyCurrentBid();

	StringProperty propertyTimeLeft();

	StringProperty propertyEventText();

	BooleanProperty propertyIsSold();

	StringProperty propertySeller();

	StringProperty propertyHighestBidder();

	StringProperty propertyDescription();

	StringProperty propertyTags();

	ObjectProperty<Paint> propertyEventColor();
}
