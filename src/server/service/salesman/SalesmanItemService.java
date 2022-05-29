package server.service.salesman;

import shared.SaleStrategyType;

import java.time.LocalDate;
import java.time.LocalTime;

public interface SalesmanItemService {
	void createAndSendItemToDB(String title, String description, String tags, SaleStrategyType saleType, String username, double offer, LocalTime endtime, LocalDate endDate);
}
