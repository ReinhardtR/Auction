package client.views.itemlist;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.model.item.ItemCacheProxy;
import client.model.item.ObservableItem;
import client.views.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import shared.SaleStrategyType;

public class ItemListController implements ViewController {

	@FXML
	private TableColumn<ItemCacheProxy, String> titleCol;
	@FXML
	private TableColumn<ObservableItem, SaleStrategyType> typeCol;
	@FXML
	private TableColumn<ItemCacheProxy, String> tagsCol;
	@FXML
	private TableColumn<ItemCacheProxy, String> sellerCol;
	@FXML
	private TableView<ItemCacheProxy> itemsTableView;

	@FXML
	private ItemListViewModel itemListViewModel;

	public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory) {
		titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
		tagsCol.setCellValueFactory(new PropertyValueFactory<>("tags"));
		typeCol.setCellValueFactory(new PropertyValueFactory<>("strategyType"));
		sellerCol.setCellValueFactory(new PropertyValueFactory<>("salesmanUsername"));

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

	@FXML
	protected void openSaleView() {
		itemListViewModel.openSaleView();
	}
}
