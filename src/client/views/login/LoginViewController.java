package client.views.login;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.views.ViewController;
import javafx.scene.control.TextField;

public class LoginViewController implements ViewController {
	public TextField username;

	private ViewModelFactory viewModelFactory;
	private ViewHandler viewHandler;

	@Override
	public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory) {
		this.viewHandler = viewHandler;
		this.viewModelFactory = viewModelFactory;
	}
}
