package client.core;

import client.model.ChatManager;
import client.model.ChatModel;

public class ModelFactory {
	private static final ModelFactory instance = new ModelFactory();
	private ChatModel model;

	private ModelFactory() {
	}

	public static ModelFactory getInstance() {
		return instance;
	}


	public ChatModel getModel() {
		if (model == null) {
			model = new ChatManager(ClientFactory.getInstance().getClient());
		}

		return model;
	}
}
