package client.model;

import client.network.Client;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ModelManager implements Model {

	private final PropertyChangeSupport support;
	private final Client client;

	public ModelManager(Client client) {
		support = new PropertyChangeSupport(this);

		this.client = client;
		client.startClient();
		client.addListener("NEW_MESSAGE", this::onNewMessage);
	}

	private void onNewMessage(PropertyChangeEvent event) {
		support.firePropertyChange(event);
	}

	@Override
	public void sendMessage(String content) {
		client.sendMessage(content);
	}

	@Override
	public void addListener(String eventName, PropertyChangeListener listener) {
		support.addPropertyChangeListener(eventName, listener);
	}

	@Override
	public void removeListener(String eventName, PropertyChangeListener listener) {
		support.removePropertyChangeListener(eventName, listener);
	}
}
