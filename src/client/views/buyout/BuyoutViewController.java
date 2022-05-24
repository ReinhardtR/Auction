package client.views.buyout;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.views.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class BuyoutViewController implements ViewController {

	// brug samme naming convention i begge view controllers
	@FXML
	private Button buyButton;
	@FXML
	private Label timeLeftOnBid;
	@FXML
	private Label price;
	@FXML
	private Label itemLabel;
	@FXML
	private Label errorLabel;

	private BuyoutViewModel viewModel;

	@Override
	public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory) {
		// brug samme naming convention i begge viewmodels
		viewModel = viewModelFactory.getBuyoutViewModel();

		buyButton.disableProperty().bind(viewModel.getIsSoldProperty());
		itemLabel.textProperty().bind(viewModel.getItemNameProperty());
		price.textProperty().bind(viewModel.getPriceProperty().asString());
		errorLabel.textProperty().bind(viewModel.getErrorProperty());
	}

	@FXML
	protected void onBuy() {
		viewModel.onBuy(Double.parseDouble(price.getText()), "Jens");
	}

	public void returnToList() {
		viewModel.returnToItemListView();
	}
}
