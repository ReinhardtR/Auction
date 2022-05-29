package client.views.login;

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

		format(userNameField,20);
	}

	@FXML
	protected void onLogin() {
		loginViewModel.login(userNameField.getText());
	}

	private void format(TextField textField, int size)
	{
		Pattern pattern = Pattern.compile(".{0,"+size+"}");
		TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
			return pattern.matcher(change.getControlNewText()).matches() ? change : null;
		});

		textField.setTextFormatter(formatter);
	}

}
