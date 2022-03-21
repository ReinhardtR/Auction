package client.network;

import shared.util.PropertyChangeSubject;

public interface Client extends PropertyChangeSubject {
	void sendMessage(String str);

	void startClient();
}
