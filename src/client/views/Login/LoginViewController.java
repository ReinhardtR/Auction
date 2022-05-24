package client.views.Login;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.views.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

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

		Pattern pattern = Pattern.compile(".{0,30}");
		TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
			return pattern.matcher(change.getControlNewText()).matches() ? change : null;
		});

		userNameField.setTextFormatter(formatter);
	}


	@FXML
	public void onLogin() {
		loginViewModel.login(userNameField.getText());
	}
}
