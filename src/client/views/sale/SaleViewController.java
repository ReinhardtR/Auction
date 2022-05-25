package client.views.sale;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.views.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import shared.SaleStrategyType;

public class SaleViewController implements ViewController {

	@FXML
	private Label eventCreateLabel;

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

	@Override
	public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory) {
		saleViewModel = viewModelFactory.getSaleViewModel();

		price_offerLabel.setVisible(false);
		price_BidTextField.setVisible(false);
		endDateLabel.setVisible(false);
		endTimeTextField.setVisible(false);
		endDatePicker.setVisible(false);

		title.textProperty().bindBidirectional(saleViewModel.titleTextProperty());
		description.textProperty().bindBidirectional(saleViewModel.descriptionTextProperty());
		tags.textProperty().bindBidirectional(saleViewModel.tagsTextProperty());
		price_BidTextField.textProperty().bindBidirectional(saleViewModel.priceBidTextProperty());
		endTimeTextField.textProperty().bindBidirectional(saleViewModel.endTimeTextProperty());

		eventCreateLabel.textProperty().bindBidirectional(saleViewModel.eventLabelTextProperty());
		eventCreateLabel.textFillProperty().bind(saleViewModel.eventLabelPaintProperty());
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

	@FXML
	protected void setItemUpForSale(ActionEvent actionEvent) {
		saleViewModel.setItemUpForSale(saleType);
	}

	@FXML
	protected void returnToItemList(ActionEvent actionEvent) {
		saleViewModel.returnToItemList();
	}
}
