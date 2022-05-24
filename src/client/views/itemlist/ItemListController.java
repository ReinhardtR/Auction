package client.views.itemlist;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.model.item.ItemCacheProxy;
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
	private TableColumn<ObservableItem, SaleStrategyType> typeCol;

	@FXML
	private TableColumn<ItemCacheProxy, String> idCol;

	@FXML
	private TableView<ItemCacheProxy> itemsTableView;

	@FXML
	private ItemListViewModel itemListViewModel;

	public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory) {
		idCol.setCellValueFactory(new PropertyValueFactory<>("itemID"));
		typeCol.setCellValueFactory(new PropertyValueFactory<>("strategyType"));

		System.out.println("kalder her");
		itemsTableView.setItems(viewModelFactory.getItemListViewModel().getObservableItemList());

		itemListViewModel = viewModelFactory.getItemListViewModel();
	}

	@FXML
	protected void getNewViewForItem() {
		ItemCacheProxy item = itemsTableView.getSelectionModel().getSelectedItem();

		if (item != null) {
			itemListViewModel.openViewForItem(item);
		}
	}

	public void openSaleView() {
		itemListViewModel.openSaleView();
	}
}
