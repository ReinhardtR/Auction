package client.views.itemlist;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.model.ObservableItem;
import client.views.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ItemListController implements ViewController {

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
	}

	public void getNewViewForItem() {
		ObservableItem observableItem = (itemsTableView.getSelectionModel().getSelectedItem());

		if (itemListViewModel.getStrategyOnItem(observableItem).equals("Auction")) {
			ViewHandler.getInstance().openAuctionView();
		}

	}
}
