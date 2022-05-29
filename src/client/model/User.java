package client.model;

import client.model.item.ObservableItem;
import shared.SaleStrategyType;

import java.time.LocalDate;
import java.time.LocalTime;

public interface User {

	String getUsername();

	void setUsername(String username);

	void makeOfferOnItem(double offerAmount, ObservableItem item);

	void createItem(String title, String description, String tags, SaleStrategyType saleType, double offer, LocalTime time, LocalDate date);
}
