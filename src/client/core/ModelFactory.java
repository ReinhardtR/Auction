package client.core;

import client.model.ModelManager;

public class ModelFactory {
	private static final ModelFactory instance = new ModelFactory();
	private ModelManager model;

	private ModelFactory() {
	}

	public static ModelFactory getInstance() {
		return instance;
	}


	public ModelManager getModel() {
		if (model == null) {
			model = new ModelManager(ClientFactory.getInstance().getClient());
		}

		return model;
	}
}
