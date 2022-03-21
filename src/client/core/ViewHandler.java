package client.core;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewHandler {

	private static final ViewHandler instance = new ViewHandler();
	private Stage stage;
	private Stage loginStage;

	private ViewHandler() {
	}

	public static ViewHandler getInstance() {
		return instance;
	}

	public void start() {
		stage = new Stage();
		loginStage = new Stage();

		openLoginView();
		openAuctionView();
	}

	public void openLoginView() {
		Scene loginScene = ViewFactory.getScene("Login");
		loginStage.setScene(loginScene);
		loginStage.show();
	}

	public void openAuctionView() {
		Scene auctionScene = ViewFactory.getScene("Auction");
		stage.setScene(auctionScene);
		stage.show();
	}
}
