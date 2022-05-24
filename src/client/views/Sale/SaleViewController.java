package client.views.Sale;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import shared.SaleStrategyType;

public class SaleViewController {


	@FXML
	private TextField title;
	@FXML
	private TextArea description;
	@FXML
	private TextField tags;


	@FXML
	private Label price_offerLabel;
	@FXML
	private TextField price_BidTextField;
	@FXML
	private Label endDateLabel;
	@FXML
	private TextField endTimeTextField;
	@FXML
	private DatePicker endDatePicker;

	private SaleStrategyType saleType;

	private SaleViewModel saleViewModel;

	public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory) {
		saleViewModel = viewModelFactory.getSaleViewModel();

		price_offerLabel.setVisible(false);
		price_BidTextField.setVisible(false);
		endDateLabel.setVisible(false);
		endTimeTextField.setVisible(false);
		endDatePicker.setVisible(false);

		title.textProperty().bind(saleViewModel.titleTextProperty());
		description.textProperty().bind(saleViewModel.descriptionTextProperty());
		tags.textProperty().bind(saleViewModel.tagsTextProperty());
		price_BidTextField.textProperty().bind(saleViewModel.priceBidTextProperty().asString());


	}

	public void auctionSaleTypeConfig(ActionEvent actionEvent) {

		price_offerLabel.setText("Starting Bid");
		if (!(price_offerLabel.isVisible())) {
			price_offerLabel.setVisible(true);
			price_BidTextField.setVisible(true);
		}

		if (!(endDateLabel.isVisible())) {
			endDateLabel.setVisible(true);
			endTimeTextField.setVisible(true);
			endDatePicker.setVisible(true);
		}


		saleType = SaleStrategyType.AUCTION;
	}

	public void buyoutSaleTypeConfig(ActionEvent actionEvent) {
		price_offerLabel.setText("Price");
		if (!(price_offerLabel.isVisible())) {
			price_offerLabel.setVisible(true);
			price_BidTextField.setVisible(true);
		}

		if (endDateLabel.isVisible()) {
			endDateLabel.setVisible(false);
			endTimeTextField.setVisible(false);
			endDatePicker.setVisible(false);
		}

		saleType = SaleStrategyType.BUYOUT;
	}

	public void setItemUpForSale(ActionEvent actionEvent) {


	}

	public void returnToItemList(ActionEvent actionEvent) {
		saleViewModel.returnToItemList();
	}
}
