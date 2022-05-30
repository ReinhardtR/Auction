package server.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.service.salesman.SalesmanItemService;
import server.service.salesman.SalesmanItemServiceImpl;
import shared.SaleStrategyType;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SalesmanServiceImplTest {
	private static final SalesmanItemService salesmanItemService = new SalesmanItemServiceImpl();
	private static final String salesmanUsername = "Salesman";
	private static final String title = "Title";
	private static final String description = "Description";
	private static final String tags = "Tags";
	private static final LocalTime endTime = LocalTime.now();

	private static final LocalDate endDate = LocalDate.now();

	@Test
	@DisplayName("Test the construction of an Item through the SalesmanServiceImpl class")
	void testItemConstruction() {
		assertDoesNotThrow(() -> salesmanItemService.createAndSendItemToDB(title, description, tags, SaleStrategyType.AUCTION, salesmanUsername, 0, endTime, endDate));
		assertDoesNotThrow(() -> salesmanItemService.createAndSendItemToDB(title, description, tags, SaleStrategyType.BUYOUT, salesmanUsername, 0, endTime, endDate));
	}
}
