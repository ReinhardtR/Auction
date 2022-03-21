package client;

import client.core.ViewHandler;
import javafx.application.Application;
import javafx.stage.Stage;

public class AuctionApplication extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		ViewHandler.getInstance().start();
	}
}