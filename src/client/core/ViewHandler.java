package client.core;

import javafx.scene.Scene;
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
		openAuctionListView();
	}

	public void openAuctionListView() {
		Scene auctionListScene = ViewFactory.getScene("ItemList");
		stage.setScene(auctionListScene);
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
