package client.views.buyout;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.views.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.beans.PropertyChangeEvent;

public class BuyoutViewController implements ViewController {

	// brug samme naming convention i begge view controllers
	public Button buyButton;
	public Label timeLeftOnBid;
	public Label price;
	public Label itemLabel;
	public Label errorLabel;
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
