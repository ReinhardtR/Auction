package server.model;

import shared.transferobjects.Message;
import shared.util.PropertyChangeSubject;

import java.util.List;

public interface Chat extends PropertyChangeSubject {
	void sendMessage(String content);

	List<Message> getMessages();
}
