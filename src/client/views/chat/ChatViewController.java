package client.views.chat;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.views.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ChatViewController implements ViewController {
	@FXML
	private TextField messageInput;

	@FXML
	private TextField response;

	private ChatViewModel chatViewModel;

	@Override
	public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory) {
		this.chatViewModel = viewModelFactory.getChatViewModel();

		response.textProperty().bind(chatViewModel.replyProperty());
	}

	@FXML
	public void sendMessage() {
		System.out.println("Send message");
		
		String message = messageInput.getText();
		chatViewModel.sendMessage(message);
	}
}
