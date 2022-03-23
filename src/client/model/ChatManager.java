package client.model;

import client.network.Client;
import shared.transferobjects.Message;
import shared.util.PropertyChangeSubject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ChatManager implements ChatModel, PropertyChangeSubject {
	private Client client;
	private PropertyChangeSupport support;

	public ChatManager(Client client) {
		support = new PropertyChangeSupport(this);

		this.client = client;
		client.addListener("MessageReceived", this::messageReceived);
	}

	public void sendMessage(String message) {
		client.sendMessage(message);
	}

	public void messageReceived(PropertyChangeEvent event) {
		Message message = (Message) event.getNewValue();
		support.firePropertyChange("MessageReceived", null, message);
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
