package client.views.auction;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.views.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class AuctionViewController implements ViewController {

	@FXML
	private Button bidButton;

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
		inputError.textProperty().bind(auctionViewModel.propertyErrorText());

		bidButton.disableProperty().bind(auctionViewModel.propertyIsSold());

		Pattern pattern = Pattern.compile(".{0,20}");
		TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
			return pattern.matcher(change.getControlNewText()).matches() ? change : null;
		});

		bidInput.setTextFormatter(formatter);
	}

	@FXML
	protected void bidOnItem() {
		auctionViewModel.bidOnItem(bidInput.getText());
	}

	public void returnToList() {
		auctionViewModel.returnToItemListView();
	}
}
