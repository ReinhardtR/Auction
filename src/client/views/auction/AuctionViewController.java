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
	private Label seller;
	@FXML
	private Label highestBidderText;
	@FXML
	private Label descriptionText;
	@FXML
	private Label tagsText;
	@FXML
	private Label itemLabel;

	@FXML
	private Button bidButton;

	@FXML
	private Label currentBid;

	@FXML
	private Label timeLeftOnAuction;

	@FXML
	private TextField bidInput;

	@FXML
	private Label eventLabel;

	private AuctionViewModel auctionViewModel;

	@Override
	public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory) {
		auctionViewModel = viewModelFactory.getAuctionViewModel();

		itemLabel.textProperty().bind(auctionViewModel.propertyItemName());
		currentBid.textProperty().bind(auctionViewModel.propertyCurrentBid().asString());
		timeLeftOnAuction.textProperty().bind(auctionViewModel.propertyTimeLeft());

		eventLabel.textProperty().bind(auctionViewModel.propertyEventText());
		eventLabel.textFillProperty().bind(auctionViewModel.propertyEventColor());

		bidButton.disableProperty().bind(auctionViewModel.propertyIsSold());

		seller.textProperty().bind(auctionViewModel.propertySalesmanUsername());
		highestBidderText.textProperty().bind(auctionViewModel.propertyHighestBidder());
		descriptionText.textProperty().bind(auctionViewModel.propertyDescription());
		tagsText.textProperty().bind(auctionViewModel.propertyTags());

		format(bidInput,20);
	}

	@FXML
	protected void bidOnItem() {
		auctionViewModel.bidOnItem(bidInput.getText());
	}

	@FXML
	protected void returnToList() {
		auctionViewModel.returnToItemListView();
	}

	// Set a format that the given TextField must follow.
	private void format(TextField textField, int size) {
		Pattern pattern = Pattern.compile(".{0,"+size+"}");
		TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
			return pattern.matcher(change.getControlNewText()).matches() ? change : null;
		});

		textField.setTextFormatter(formatter);
	}
}
