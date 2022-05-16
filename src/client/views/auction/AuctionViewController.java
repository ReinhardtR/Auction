package client.views.auction;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.views.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AuctionViewController implements ViewController {

	@FXML
	private Label inputError;
	@FXML
	private Label timeLeftOnBid;
	@FXML
	private Label itemLabel;
	@FXML
	private Label currentBid;
	@FXML
	private TextField bidInput;

	private AuctionViewModel auctionViewModel;

	@Override
	public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory) {
		this.auctionViewModel = viewModelFactory.getAuctionViewModel();

		itemLabel.textProperty().bind(auctionViewModel.propertyItemLabel());
		currentBid.textProperty().bind(auctionViewModel.propertyCurrentBid().asString());
		timeLeftOnBid.textProperty().bind(auctionViewModel.propertyTimeLeft());

		inputError.setText("");
	}

	@FXML
	protected void bidOnItem() {
		try {
			auctionViewModel.bidOnItem(Double.parseDouble(bidInput.getText()));
			inputError.setText("");
		} catch (NumberFormatException e) {
			inputError.setText("You have to type in a number!");
		}
	}
}
