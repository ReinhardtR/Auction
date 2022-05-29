package client.views.sale;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.views.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import shared.SaleStrategyType;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class SaleViewController implements ViewController {

	@FXML
	private Label endTimeLabel;
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
		endTimeLabel.setVisible(false);

		title.textProperty().bindBidirectional(saleViewModel.titleTextProperty());
		description.textProperty().bindBidirectional(saleViewModel.descriptionTextProperty());
		tags.textProperty().bindBidirectional(saleViewModel.tagsTextProperty());
		price_BidTextField.textProperty().bindBidirectional(saleViewModel.priceBidTextProperty());
		endTimeTextField.textProperty().bindBidirectional(saleViewModel.endTimeTextProperty());

		eventCreateLabel.textProperty().bindBidirectional(saleViewModel.eventLabelTextProperty());
		eventCreateLabel.textFillProperty().bind(saleViewModel.eventLabelPaintProperty());

		format(title,20);
		format(tags,30);
		format(price_BidTextField,20);
		format(description, 300);

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
			endTimeLabel.setVisible(true);
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
			endTimeLabel.setVisible(false);
		}

		saleType = SaleStrategyType.BUYOUT;
	}

	@FXML
	protected void setItemUpForSale(ActionEvent actionEvent) {
		saleViewModel.setItemUpForSale(saleType, endDatePicker.getValue());
	}

	@FXML
	protected void returnToItemList(ActionEvent actionEvent) {
		saleViewModel.returnToItemList();
	}

	private void format(TextField textField, int size)
	{
		Pattern pattern = Pattern.compile(".{0,"+size+"}");
		TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
			return pattern.matcher(change.getControlNewText()).matches() ? change : null;
		});

		textField.setTextFormatter(formatter);
	}
	private void format(TextArea textArea, int size)
	{
		Pattern pattern = Pattern.compile(".{0,"+size+"}");
		TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
			return pattern.matcher(change.getControlNewText()).matches() ? change : null;
		});

		textArea.setTextFormatter(formatter);
	}
}
