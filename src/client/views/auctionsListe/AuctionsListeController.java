package client.views.auctionsListe;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.model.ObservableItem;
import client.views.ViewController;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AuctionsListeController implements ViewController {

	@FXML
	public TableView<ObservableItem> itemsTableView;

	public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory) {
		tableSetUp(viewModelFactory.getAuctionsListeViewModel().getObservableItemList());
	}

	public void getNewAuctionView() {
		ViewHandler.getInstance().openAuctionView();
	}

	private void tableSetUp(ObservableList<ObservableItem> observableList) {
		System.out.println("Size: " + observableList.size());
		itemsTableView = new TableView<>();

		TableColumn<ObservableItem, String> idCol = new TableColumn<>("ID");
		idCol.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getItemID()));

		itemsTableView.getColumns().add(idCol);
		itemsTableView.getItems().addAll(observableList);
	}
}
