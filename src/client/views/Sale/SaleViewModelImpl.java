package client.views.Sale;

import client.core.ViewHandler;
import client.model.UsernameModel;
import client.utils.ViewEnum;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

import java.time.chrono.Chronology;

public class SaleViewModelImpl implements SaleViewModel {

	private final UsernameModel usernameModel;
	private final StringProperty titleTextProperty;
	private final StringProperty descriptionTextProperty;
	private final StringProperty tagsTextProperty;
	private final DoubleProperty priceOfferProperty;
	private final StringProperty endTimeProperty;
	private final StringProperty errorLabelProperty;

	private Chronology endDateProperty;

	public SaleViewModelImpl(UsernameModel userNameModel) {
		this.usernameModel = userNameModel;

		titleTextProperty = new SimpleStringProperty();
		descriptionTextProperty = new SimpleStringProperty();
		tagsTextProperty = new SimpleStringProperty();
		priceOfferProperty = new SimpleDoubleProperty();
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
	public DoubleProperty priceBidTextProperty() {
		return priceOfferProperty;
	}

	@Override
	public void returnToItemList() {
		ViewHandler.getInstance().openView(ViewEnum.ItemList.toString());
	}

	@Override
	public void setItemUpForSale() {
		try {
			if (titleTextProperty.toString().isEmpty() || priceOfferProperty.toString().isEmpty()
							|| endTimeProperty.toString().isEmpty()) {
				errorLabelProperty.setValue("Please fill out all required fields!");
			} else {
				usernameModel.createItem();
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
