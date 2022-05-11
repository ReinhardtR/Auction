package client.views.auctionsListe;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.model.ObservableItem;
import client.network.ClientImpl;
import client.network.LocalClient;
import client.views.ViewController;
import client.views.auction.AuctionViewModel;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import server.model.item.ItemImpl;
import shared.network.model.Item;

public class AuctionsListeController implements ViewController {
	@FXML
	private TableColumn<ObservableItem, String> ID;
	@FXML
	private TableView<ObservableItem> itemsTableView;
	private ObservableList<ObservableItem> list;

	private AuctionsListeViewModel auctionsListeViewModel;

	public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory) {
		this.auctionsListeViewModel = viewModelFactory.getAuctionsListeViewModel();

		ID.setCellValueFactory(new PropertyValueFactory<ObservableItem,String>("itemID"));
		list = auctionsListeViewModel.getObservableItemList();
		itemsTableView.setItems(list);
	}

	public void getNewAuctionView() {
		ViewHandler.getInstance().openAuctionView();
	}
}
