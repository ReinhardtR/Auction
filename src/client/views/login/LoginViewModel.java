package client.views.login;

import javafx.beans.property.StringProperty;

public interface LoginViewModel {

	StringProperty getErrorTextProperty();

	void login(String username);
}
