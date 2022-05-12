package client.views.buyout;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.views.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BuyoutViewController implements ViewController {
	public Label timeLeftOnBid;
	public Label price;
	public Label itemLabel;
	private BuyoutViewModel viewModel;

	@Override
	public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory) {
		viewModel = viewModelFactory.getBuyoutViewModel();
	}

	@FXML
	public void buyItem() {

	}

	public void onBuy() {
	}
}
