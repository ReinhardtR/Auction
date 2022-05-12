package client.views.auctionsListe;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.model.ObservableItem;
import client.views.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AuctionsListeController implements ViewController {

	@FXML
	private TableColumn<ObservableItem, String> idCol;

	@FXML
	private TableView<ObservableItem> itemsTableView;

	public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory) {
		idCol.setCellValueFactory(new PropertyValueFactory<ObservableItem, String>("itemID"));
		itemsTableView.setItems(viewModelFactory.getAuctionsListeViewModel().getObservableItemList());
	}

	public void getNewAuctionView() {
		ViewHandler.getInstance().openAuctionView();
	}
}
