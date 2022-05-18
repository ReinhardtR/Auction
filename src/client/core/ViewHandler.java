package client.core;

import javafx.stage.Stage;

public class ViewHandler {
	private static final ViewHandler instance = new ViewHandler();
	private Stage stage;

	private ViewHandler() {
	}

	public static ViewHandler getInstance() {
		return instance;
	}

	public void start() {
		stage = new Stage();
		ViewFactory.init(stage);
		openItemListView();
	}

	public void openItemListView() {
		stage.setScene(ViewFactory.getScene("ItemList"));
		stage.show();
	}

	public void openAuctionView() {
		stage.setScene(ViewFactory.startNewScene("Auction"));
		stage.show();
	}

	public void openBuyoutView() {
		stage.setScene(ViewFactory.startNewScene("Buyout"));
		stage.show();
	}
}
