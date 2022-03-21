package client.model;

import shared.util.PropertyChangeSubject;

public interface Model extends PropertyChangeSubject {
	void sendMessage(String content);

	void another();
}
