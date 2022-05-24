package client.views.buyout;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.views.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class BuyoutViewController implements ViewController {

	@FXML
	private Label itemLabel;

	@FXML
	private Button buyButton;

	@FXML
	private Label price;

	@FXML
	private Label errorLabel;

	private BuyoutViewModel buyoutViewModel;

	@Override
	public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory) {
		buyoutViewModel = viewModelFactory.getBuyoutViewModel();

		buyButton.disableProperty().bind(buyoutViewModel.propertyIsSold());
		itemLabel.textProperty().bind(buyoutViewModel.propertyItemName());
		price.textProperty().bind(buyoutViewModel.propertyPrice().asString());
		errorLabel.textProperty().bind(buyoutViewModel.propertyErrorText());
	}

	@FXML
	protected void onBuy() {
		buyoutViewModel.buyItem();
	}

	@FXML
	protected void returnToList() {
		buyoutViewModel.returnToItemListView();
	}
}
