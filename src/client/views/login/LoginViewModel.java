package client.views.login;

import client.model.ChatModel;
import javafx.beans.property.StringProperty;

public class LoginViewModel {
	private final ChatModel chatModel;
	private StringProperty username;

	public LoginViewModel(ChatModel chatModel) {
		this.chatModel = chatModel;
	}
}
