package client.views.Login;

import client.core.ViewHandler;
import client.utils.ViewEnum;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LoginViewModelImpl implements LoginViewModel {

	private StringProperty errorTextProperty;

	public LoginViewModelImpl() {
		errorTextProperty = new SimpleStringProperty();
	}

	@Override
	public StringProperty getErrorTextProperty() {
		return errorTextProperty;
	}

	@Override
	public void login(String username) {
		System.out.println("BLACK");
		if (username.isBlank()) {
			errorTextProperty.setValue("Please type in a username!");
		} else if (username.length() > 20) {
			errorTextProperty.setValue("Please type in a shorter username");
		} else {
			ViewHandler.getInstance().openView(ViewEnum.ItemList.toString());
		}
	}
}
