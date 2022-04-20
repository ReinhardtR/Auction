package client.views.auction;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.views.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AuctionViewController implements ViewController {

	@FXML
	private Label currentBid;

	@FXML
	private TextField bidInput;

	private AuctionViewModel auctionViewModel;

	@Override
	public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory) {
		this.auctionViewModel = viewModelFactory.getAuctionViewModel();

		currentBid.textProperty().bind(auctionViewModel.bidProperty());
	}

	@FXML
	private void placeBid() {
		try {
			int amount = Integer.parseInt(bidInput.getText());
			auctionViewModel.makeNewBid("123", "John", amount);
		} catch (NumberFormatException e) {
			System.out.println("Please input a valid number");
		}
	}
}
