package client.views.login;

import client.model.Model;
import javafx.beans.property.StringProperty;

public class LoginViewModel {
	private final Model model;
	private StringProperty username;

	public LoginViewModel(Model model) {
		this.model = model;
	}
}
