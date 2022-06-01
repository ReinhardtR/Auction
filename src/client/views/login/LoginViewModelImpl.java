package client.views.login;

import client.core.ViewHandler;
import client.model.User;
import client.utils.ViewEnum;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LoginViewModelImpl implements LoginViewModel {

	private final StringProperty errorTextProperty;
	private final User user;

	public LoginViewModelImpl(User user) {
		errorTextProperty = new SimpleStringProperty();
		this.user = user;
	}

	@Override
	public StringProperty getErrorTextProperty() {
		return errorTextProperty;
	}

	// Validate and set username for user.
	@Override
	public void login(String username) {
		if (username.isBlank()) {
			errorTextProperty.setValue("Please type in a username!");
		} else {
			user.setUsername(username);
			ViewHandler.getInstance().openView(ViewEnum.ItemList.toString());
		}
	}
}
