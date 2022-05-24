package client.model;

public interface User {

	String getUsername();

	void setUsername(String username);

	void createItem();

	void makeOfferOnItem(double offerAmount, ObservableItem item);
}
