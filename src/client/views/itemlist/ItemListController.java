package client.views.itemlist;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.model.ObservableItem;
import client.views.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import shared.SaleStrategyType;

public class ItemListController implements ViewController {

	@FXML
	private Label errorLabel;

	@FXML
	private TableColumn<ObservableItem, String> idCol;

	@FXML
	private TableView<ObservableItem> itemsTableView;

	@FXML
	private ItemListViewModel itemListViewModel;

	public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory) {
		idCol.setCellValueFactory(new PropertyValueFactory<>("itemID"));
		itemsTableView.setItems(viewModelFactory.getItemListViewModel().getObservableItemList());

		itemListViewModel = viewModelFactory.getItemListViewModel();

		errorLabel.setText("");
	}

	@FXML
	protected void getNewViewForItem() {
		//SKAL I VIEWMODEL
		ObservableItem observableItem = itemsTableView.getSelectionModel().getSelectedItem();

		if (observableItem != null) {
			itemListViewModel.setCurrentlyViewedItemID(observableItem.getItemID());

			// TODO: should this logic be here?
			if (itemListViewModel.getStrategyOnItem(observableItem).equals(SaleStrategyType.AUCTION)) {
				ViewHandler.getInstance().openAuctionView();
			} else if (itemListViewModel.getStrategyOnItem(observableItem).equals(SaleStrategyType.BUYOUT)) {
				ViewHandler.getInstance().openBuyoutView();
			}

			errorLabel.setText("");
		} else {
			errorLabel.setText("Select an item you wanna bid on/buy!");
		}
	}
}
