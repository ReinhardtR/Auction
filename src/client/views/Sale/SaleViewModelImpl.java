package client.views.Sale;

import client.core.ViewHandler;
import client.model.UsernameModel;
import client.utils.ViewEnum;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SaleViewModelImpl implements SaleViewModel {

	private final UsernameModel usernameModel;
	private final StringProperty titleTextProperty;
	private final StringProperty descriptionTextProperty;
	private final StringProperty tagsTextProperty;
	private final DoubleProperty priceOfferProperty;

	public SaleViewModelImpl(UsernameModel userNameModel) {
		this.usernameModel = userNameModel;

		titleTextProperty = new SimpleStringProperty();
		descriptionTextProperty = new SimpleStringProperty();
		tagsTextProperty = new SimpleStringProperty();
		priceOfferProperty = new SimpleDoubleProperty();
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
}
