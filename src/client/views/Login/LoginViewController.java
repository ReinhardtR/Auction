package client.views.Login;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.views.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginViewController implements ViewController {
	@FXML
	private Label errorLoginLabel;
	@FXML
	private TextField userNameField;


	private LoginViewModel loginViewModel;

	@Override
	public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory) {

		loginViewModel = viewModelFactory.getLoginViewModel();

		errorLoginLabel.textProperty().bind(loginViewModel.getErrorTextProperty());
	}


	@FXML
	public void onLogin() {
		loginViewModel.login(userNameField.getText());
	}
}
