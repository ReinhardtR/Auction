package client.views.auctionsListe;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.model.ObservableItem;
import client.views.ViewController;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AuctionsListeController implements ViewController {

	@FXML
	public TableView<ObservableItem> itemsTableView;
	private ObservableList<ObservableItem> observableList;
	private AuctionsListeViewModel auctionsListeViewModel;

	public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory) {
		this.auctionsListeViewModel = viewModelFactory.getAuctionsListeViewModel();

		tableSetUp();
	}

	public void getNewAuctionView() {
		ViewHandler.getInstance().openAuctionView();
	}

	private void tableSetUp() {
		Platform.runLater(() -> {
			observableList = auctionsListeViewModel.getObservableItemList();
			itemsTableView = new TableView<>();
			itemsTableView.setItems(observableList);

			TableColumn<ObservableItem, String> idCol = new TableColumn<>("ID");
			idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
			itemsTableView.getColumns().setAll(idCol);
		});

	}
}
