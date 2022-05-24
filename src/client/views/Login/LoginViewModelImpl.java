package client.views.Login;

import client.core.ViewHandler;
import client.model.UsernameModel;
import client.utils.ViewEnum;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LoginViewModelImpl implements LoginViewModel {

	private final StringProperty errorTextProperty;
	private final UsernameModel usernameModel;

	public LoginViewModelImpl(UsernameModel usernameModel) {
		errorTextProperty = new SimpleStringProperty();
		this.usernameModel = usernameModel;
	}

	@Override
	public StringProperty getErrorTextProperty() {
		return errorTextProperty;
	}

	@Override
	public void login(String username) {
		if (username.isBlank()) {
			errorTextProperty.setValue("Please type in a username!");
		} else if (username.length() > 20) {
			errorTextProperty.setValue("Please type in a shorter username");
		} else {
			usernameModel.setUsername(username);
			ViewHandler.getInstance().openView(ViewEnum.ItemList.toString());
		}
	}
}
