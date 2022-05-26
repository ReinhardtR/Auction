package client.views.sale;

import client.core.ViewHandler;
import client.model.User;
import client.utils.ViewEnum;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import shared.SaleStrategyType;

import java.time.chrono.Chronology;

public class SaleViewModelImpl implements SaleViewModel {

	private final User salesman;
	private final StringProperty titleTextProperty;
	private final StringProperty descriptionTextProperty;
	private final StringProperty tagsTextProperty;
	private final StringProperty priceOfferProperty;
	private final StringProperty endTimeProperty;
	private final StringProperty eventLabelTextProperty;
	private final ObjectProperty<Paint> eventLabelColorProperty;

	private Chronology endDateProperty;

	public SaleViewModelImpl(User salesman) {
		this.salesman = salesman;

		titleTextProperty = new SimpleStringProperty();
		descriptionTextProperty = new SimpleStringProperty();
		tagsTextProperty = new SimpleStringProperty();
		priceOfferProperty = new SimpleStringProperty();
		endTimeProperty = new SimpleStringProperty();
		eventLabelTextProperty = new SimpleStringProperty();
		eventLabelColorProperty = new SimpleObjectProperty<>();
	}

	@Override
	public StringProperty titleTextProperty() {
		return titleTextProperty;
	}

	@Override
	public StringProperty descriptionTextProperty() {
		return descriptionTextProperty;
	}

	@Override
	public StringProperty tagsTextProperty() {
		return tagsTextProperty;
	}

	@Override
	public StringProperty priceBidTextProperty() {
		return priceOfferProperty;
	}

	@Override
	public void returnToItemList() {
		ViewHandler.getInstance().openView(ViewEnum.ItemList.toString());
	}

	@Override
	public void setItemUpForSale(SaleStrategyType saleType) {
		try {
			double offer = Double.parseDouble(priceOfferProperty.getValue());
			titleTextProperty.getValue();
			if ((priceOfferProperty.getValue().isBlank() || endTimeProperty.getValue().isBlank()) && saleType == SaleStrategyType.AUCTION || (titleTextProperty.getValue().isBlank() || priceOfferProperty.getValue().isBlank()) && saleType == SaleStrategyType.BUYOUT) {

				eventLabelTextProperty.setValue("Please fill out all required fields!");
				eventLabelColorProperty.setValue(Color.RED);
			} else {
				salesman.createItem(titleTextProperty.getValue(), descriptionTextProperty.getValue(), tagsTextProperty.getValue(), saleType, offer, endTimeProperty.getValue());
				eventLabelTextProperty.setValue("Item was successfully put up for sale!");
				eventLabelColorProperty.setValue(Color.GREEN);
			}
		} catch (NumberFormatException | NullPointerException e) {
			eventLabelTextProperty.setValue("Please type in a valid number in price/starter bid!");
			eventLabelColorProperty.setValue(Color.RED);
		}
	}

	@Override
	public StringProperty eventLabelTextProperty() {
		return eventLabelTextProperty;
	}

	@Override
	public StringProperty endTimeTextProperty() {
		return endTimeProperty;
	}

	@Override
	public ObservableValue<? extends Chronology> endDateChronologyProperty() {
		return null;
	}

	@Override
	public ObjectProperty<Paint> eventLabelPaintProperty() {
		return eventLabelColorProperty;
	}
}
