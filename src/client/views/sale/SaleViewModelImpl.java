package client.views.sale;

import client.core.ViewHandler;
import client.model.User;
import client.utils.ViewEnum;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import shared.SaleStrategyType;

import java.time.LocalDate;
import java.time.LocalTime;

public class SaleViewModelImpl implements SaleViewModel {

	private final User salesman;
	private final StringProperty titleTextProperty;
	private final StringProperty descriptionTextProperty;
	private final StringProperty tagsTextProperty;
	private final StringProperty priceOfferProperty;
	private final StringProperty endTimeProperty;
	private final StringProperty eventLabelTextProperty;
	private final ObjectProperty<Paint> eventLabelColorProperty;
	private final ObjectProperty<LocalDate> endDateProperty;

	public SaleViewModelImpl(User salesman) {
		this.salesman = salesman;

		endDateProperty = new SimpleObjectProperty<>();
		titleTextProperty = new SimpleStringProperty();
		descriptionTextProperty = new SimpleStringProperty();
		tagsTextProperty = new SimpleStringProperty();
		priceOfferProperty = new SimpleStringProperty();
		endTimeProperty = new SimpleStringProperty();
		eventLabelTextProperty = new SimpleStringProperty();
		eventLabelColorProperty = new SimpleObjectProperty<>();
	}


	@Override
	public void returnToItemList() {
		ViewHandler.getInstance().openView(ViewEnum.ItemList.toString());
	}

	// Validate user input (set error messages on exceptions)
	// Create sale and show success message on successful input.
	@Override
	public void setItemUpForSale(SaleStrategyType saleType) {
		try {
			double offer = Double.parseDouble(priceOfferProperty.getValue());

			if (offer <= 0) {
				eventLabelTextProperty.setValue("Please type in a price/starter bid higher than 0!");
				eventLabelColorProperty.setValue(Color.RED);
			} else if (!(titleTextProperty.getValue().isBlank()) && saleType == SaleStrategyType.BUYOUT) {
				createSale(saleType, offer, LocalTime.now(), LocalDate.now());
			} else if (!(endTimeProperty.getValue().isBlank()) && dateChecker(endDateProperty.getValue()) && saleType == SaleStrategyType.AUCTION) {
				LocalTime endTime = inputToTimeConverter();
				if (endTime != null) {
					createSale(saleType, offer, endTime, endDateProperty.getValue());
				}
			}

		} catch (NumberFormatException e) {
			eventLabelTextProperty.setValue("Please type in a valid number in price/starter bid!");
			eventLabelColorProperty.setValue(Color.RED);
		} catch (NullPointerException e) {
			eventLabelTextProperty.setValue("Please fill out all required fields!");
			eventLabelColorProperty.setValue(Color.RED);
		}
	}

	// Convert the string time input, to a LocalTime object.
	private LocalTime inputToTimeConverter() {
		try {
			String[] time = endTimeProperty.getValue().split("/");
			return LocalTime.of(Integer.parseInt(time[0]), Integer.parseInt(time[1]), Integer.parseInt(time[2]));
		} catch (Exception e) {
			eventLabelTextProperty.setValue("Please type in end time as instructed!");
			eventLabelColorProperty.setValue(Color.RED);
		}

		return null;
	}

	// Validate that the date is not null and is not in the past.
	private boolean dateChecker(LocalDate date) {
		if (date == null) {
			eventLabelTextProperty.setValue("Please choose an end date!");
			eventLabelColorProperty.setValue(Color.RED);
			return false;
		} else if (LocalDate.now().isEqual(date) || LocalDate.now().isBefore(date)) {
			return true;
		} else {
			eventLabelTextProperty.setValue("Please choose an end day from today and forward!");
			eventLabelColorProperty.setValue(Color.RED);
			return false;
		}
	}

	private void createSale(SaleStrategyType saleType, double offer, LocalTime time, LocalDate date) {
		salesman.createItem(titleTextProperty.getValue(), descriptionTextProperty.getValue(), tagsTextProperty.getValue(), saleType, offer, time, date);
		eventLabelTextProperty.setValue("Item was successfully put up for sale!");
		eventLabelColorProperty.setValue(Color.GREEN);
	}

	@Override
	public ObjectProperty<LocalDate> endDateProperty() {
		return endDateProperty;
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
	public StringProperty eventLabelTextProperty() {
		return eventLabelTextProperty;
	}

	@Override
	public StringProperty endTimeTextProperty() {
		return endTimeProperty;
	}

	@Override
	public ObjectProperty<Paint> eventLabelPaintProperty() {
		return eventLabelColorProperty;
	}
}
