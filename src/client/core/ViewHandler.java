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
		openChatView();
	}

	public void openChatView() {
		Scene chatScene = ViewFactory.getScene("Chat");
		stage.setScene(chatScene);
		stage.show();
	}

	public void openLoginView() {
		Scene loginScene = ViewFactory.getScene("Login");
		stage.setScene(loginScene);
		stage.show();
	}

	public void openAuctionView() {
		Scene auctionScene = ViewFactory.getScene("Auction");
		stage.setScene(auctionScene);
		stage.show();
	}
}
