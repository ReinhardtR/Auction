package client.core;

import client.utils.ViewEnum;
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
		String viewLogin = ViewEnum.Login.toString();

		stage = new Stage();
		ViewFactory.init(stage, viewLogin);
		openView(viewLogin);
	}

	public void openView(String viewName) {
		stage.setScene(ViewFactory.getScene(viewName));
		stage.show();
	}

	public void openView(ViewEnum viewEnum, String viewName) {
		stage.setScene(ViewFactory.getScene(viewEnum, viewName));
	}

}
