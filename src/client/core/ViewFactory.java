package client.core;

import client.utils.ViewEnum;
import client.views.ViewController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class ViewFactory {
	private static final Map<String, Scene> scenes = new HashMap<>();
	private static Stage stage;

	public static void init(Stage theStage, String viewLogin) {
		stage = theStage;
		startNewScene(viewLogin);

		stage.setOnCloseRequest(windowEvent -> {
			try {
				ClientFactory.getInstance().getClient().unregisterClient();
			} catch (RemoteException e) {
				e.printStackTrace();
			}

			Platform.exit();
			System.exit(0);
		});
	}

	private static Scene createScene(String sceneName) {
		try {
			System.out.println("Create " + sceneName + " Scene");
			String path = "../views/" + sceneName.toLowerCase() + "/" + sceneName + "View.fxml";
			Parent root = loadFXML(path);
			Scene scene = new Scene(root);
			return scene;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Parent loadFXML(String path) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ViewFactory.class.getResource(path));
		Parent root = loader.load();

		ViewController viewController = loader.getController();
		viewController.init(ViewHandler.getInstance(), ViewModelFactory.getInstance());
		return root;
	}

	private static Scene startNewScene(String sceneName) {
		scenes.put(sceneName, createScene(sceneName));
		return scenes.get(sceneName);
	}

	public static Scene getScene(ViewEnum viewEnum, String itemID) {
		if (scenes.containsKey(viewEnum + itemID)) {
			return scenes.get(viewEnum + itemID);
		}
		scenes.put(viewEnum + itemID, createScene(viewEnum.toString()));
		return scenes.get(viewEnum + itemID);
	}

	public static Scene getScene(String sceneName) {
		if (scenes.containsKey(sceneName)) {
			return scenes.get(sceneName);
		}
		scenes.put(sceneName, createScene(sceneName));
		return scenes.get(sceneName);
	}
}
