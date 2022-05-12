package client.views.buyout;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.views.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BuyoutViewController implements ViewController {
	@FXML
	public Label timeLeftOnBid;
	@FXML
	public Label price;
	@FXML
	public Label itemLabel;

	private BuyoutViewModel buyoutViewModel;

	@Override
	public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory) {
		this.buyoutViewModel = viewModelFactory.getBuyoutViewModel();
	}

	public void onBuy() {
	}

}
