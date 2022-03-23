package client.core;

import client.views.auction.AuctionViewModel;
import client.views.chat.ChatViewModel;
import client.views.login.LoginViewModel;

public class ViewModelFactory {
  private static final ViewModelFactory instance = new ViewModelFactory();
  private LoginViewModel loginViewModel;
  private AuctionViewModel auctionViewModel;
  private ChatViewModel chatViewModel;

  private ViewModelFactory() {
  }

  public static ViewModelFactory getInstance() {
    return instance;
  }

  public LoginViewModel getLoginViewModel() {
    if (loginViewModel == null) {
      loginViewModel = new LoginViewModel(ModelFactory.getInstance().getModel());
    }

    return loginViewModel;
  }

  public AuctionViewModel getAuctionViewModel() {
    if (auctionViewModel == null) {
      auctionViewModel = new AuctionViewModel(ModelFactory.getInstance().getModel());
    }

    return auctionViewModel;
  }

  public ChatViewModel getChatViewModel() {
    if (chatViewModel == null) {
      chatViewModel = new ChatViewModel(ModelFactory.getInstance().getModel());
    }

    return chatViewModel;
  }
}