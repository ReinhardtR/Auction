package client.views.auction;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.temporal.Temporal;

public interface AuctionViewModel extends PropertyChangeListener {

	void bidOnItem(String offerInputText);
	StringProperty propertyItemLabel();
	DoubleProperty propertyCurrentBid();
	StringProperty propertyTimeLeft();
	StringProperty propertyErrorText();
	void propertyChange(PropertyChangeEvent event);
	void returnToItemListView();
}
