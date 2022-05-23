package client.views.buyout;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.views.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BuyoutViewController implements ViewController {
	public Label timeLeftOnBid;
	public Label price;
	public Label itemLabel;
	public Label errorLabel;
	private BuyoutViewModel viewModel;

	@Override
	public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory) {
		viewModel = viewModelFactory.getBuyoutViewModel();

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
