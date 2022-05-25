package client.views.buyout;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.views.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class BuyoutViewController implements ViewController {

	@FXML
	private Label seller;
	@FXML
	private Label descriptionText;
	@FXML
	private Label tagsText;
	@FXML
	private Label buyer;
	@FXML
	private Label itemLabel;

	@FXML
	private Button buyButton;

	@FXML
	private Label price;

	@FXML
	private Label errorLabel;

	private BuyoutViewModel buyoutViewModel;

	@Override
	public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory) {
		buyoutViewModel = viewModelFactory.getBuyoutViewModel();

		buyButton.disableProperty().bind(buyoutViewModel.propertyIsSold());
		itemLabel.textProperty().bind(buyoutViewModel.propertyItemName());
		price.textProperty().bind(buyoutViewModel.propertyPrice().asString());
		errorLabel.textProperty().bind(buyoutViewModel.propertyErrorText());
		seller.textProperty().bind(buyoutViewModel.propertySellerUser());
		descriptionText.textProperty().bind(buyoutViewModel.propertyDescriptionText());
		tagsText.textProperty().bind(buyoutViewModel.propertyTagsText());
		buyer.textProperty().bind(buyoutViewModel.propertyBuyerText());
	}

	@FXML
	protected void onBuy() {
		buyoutViewModel.buyItem();
	}

	@FXML
	protected void returnToList() {
		buyoutViewModel.returnToItemListView();
	}
}
