package client.model;

import shared.SaleStrategyType;

public interface User {

	String getUsername();

	void setUsername(String username);

	void makeOfferOnItem(double offerAmount, ObservableItem item);

	void createItem(String s, String toString, String string, SaleStrategyType saleType, double v, String s1);
}
