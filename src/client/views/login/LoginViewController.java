package client.views.login;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.views.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginViewController implements ViewController {
	@FXML
	private TextField username;

	private ViewHandler viewHandler;
	private LoginViewModel loginViewModel;

	@Override
	public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory) {
		this.viewHandler = viewHandler;
		this.loginViewModel = viewModelFactory.getLoginViewModel();
	}
}
