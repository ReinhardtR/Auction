package client.views.buyout;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.views.ViewController;
import javafx.fxml.FXML;

public class BuyoutViewController implements ViewController {
	private BuyoutViewModel viewModel;

	@Override
	public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory) {
		viewModel = viewModelFactory.getBuyoutViewModel();
	}

	@FXML
	public void buyItem() {
		
	}
}
