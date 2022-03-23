package client.views.chat;

import client.model.ChatModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import shared.transferobjects.Message;

import java.beans.PropertyChangeEvent;

public class ChatViewModel {
	private final ChatModel chatManager;
	private final StringProperty reply;

	public ChatViewModel(ChatModel chatManager) {
		this.chatManager = chatManager;
		chatManager.addListener("MessageReceived", this::receiveMessage);
		reply = new SimpleStringProperty();
	}

	public void sendMessage(String message) {
		chatManager.sendMessage(message);
	}

	public void receiveMessage(PropertyChangeEvent evt) {
		Message message = (Message) evt.getNewValue();
		reply.set(message.getContent());
	}

	public StringProperty replyProperty() {
		return reply;
	}
}
