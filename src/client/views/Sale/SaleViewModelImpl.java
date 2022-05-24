package client.views.Sale;

import client.core.ViewHandler;
import client.model.User;
import client.utils.ViewEnum;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import shared.SaleStrategyType;

import java.time.chrono.Chronology;

public class SaleViewModelImpl implements SaleViewModel {

	private final User salesman;
	private final StringProperty titleTextProperty;
	private final StringProperty descriptionTextProperty;
	private final StringProperty tagsTextProperty;
	private final StringProperty priceOfferProperty;
	private final StringProperty endTimeProperty;
	private final StringProperty errorLabelProperty;

	private Chronology endDateProperty;

	public SaleViewModelImpl(User salesman) {
		this.salesman = salesman;

		titleTextProperty = new SimpleStringProperty();
		descriptionTextProperty = new SimpleStringProperty();
		tagsTextProperty = new SimpleStringProperty();
		priceOfferProperty = new SimpleStringProperty();
		endTimeProperty = new SimpleStringProperty();
		errorLabelProperty = new SimpleStringProperty();
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
			if (titleTextProperty.getValue().isBlank() || priceOfferProperty.getValue().isBlank()
							|| endTimeProperty.getValue().isBlank()) {
				errorLabelProperty.setValue("Please fill out all required fields!");
			} else {
				salesman.createItem(titleTextProperty.getValue(), descriptionTextProperty.getValue(),
								tagsTextProperty.getValue(), saleType, offer, endTimeProperty.getValue());
			}
		} catch (NumberFormatException | NullPointerException e) {
			errorLabelProperty.setValue("Please type in a valid number in price/starter bid!");
		}
	}

	@Override
	public StringProperty errorLabelProperty() {
		return errorLabelProperty;
	}

	@Override
	public StringProperty endTimeTextProperty() {
		return endTimeProperty;
	}

	@Override
	public ObservableValue<? extends Chronology> endDateChronologyProperty() {
		return null;
	}
}
