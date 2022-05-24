package client.views.Login;

import javafx.beans.property.StringProperty;

public interface LoginViewModel {

	StringProperty getErrorTextProperty();

	void login(String username);
}
