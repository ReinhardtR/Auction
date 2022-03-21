package server.model;

import shared.transferobjects.Message;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChatModel implements Chat {
	private final PropertyChangeSupport support;
	private final List<Message> messages;

	public ChatModel() {
		support = new PropertyChangeSupport(this);
		messages = new ArrayList<>();
	}

	@Override
	public void sendMessage(String content) {
		Message message = new Message(content, LocalDateTime.now());
		messages.add(message);

		support.firePropertyChange("NEW_MESSAGE", null, message);
	}

	@Override
	public List<Message> getMessages() {
		return messages;
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
