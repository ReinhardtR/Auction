package server.persistence.utils;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class SQLTest {

	@BeforeAll
	static void startSQLTables() {
		SQL.constructDatabaseTables(getConnection());
	}

	static Connection getConnection() {
		try {
			Class.forName("org.postgresql.Driver");
			return DriverManager.getConnection(
							SQL.getURL(),
							SQL.getUSERNAME(),
							SQL.getPASSWORD()
			);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		return null;
	}

	@Test
	void selectItemTest() {
		assertNotNull(SQL.selectItem("1"));
	}

	@Test
	void selectAmountOfItemsTest() {
		assertNotNull(SQL.selectAmountOfItems(5, "asc"));
	}

	@Test
	void selectSaleStrategyTest() {
		assertNotNull(SQL.selectSaleStrategy("1", "auction"));
	}

	@Test
	void selectSaleStrategyDomainTest() {
		assertNull(SQL.selectSaleStrategy("1", "test"));
	}

	@Test
	void auctionBoughtTest() {
		assertNotNull(SQL.auctionBought("1"));
		System.out.println(SQL.auctionBought("1"));
	}

	@Test
	void auctionNewBidTest() {
		assertNotNull(SQL.auctionNewBid("1", 25.05, "test"));
	}

	@Test
	void auctionsSoonToFinishTest() {
		LocalDateTime localTimeNow = LocalDateTime.now();
		String localTimeInWantedStringFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(localTimeNow.plusHours(1));
		assertNotNull(SQL.auctionsSoonToFinish(localTimeInWantedStringFormat));
		System.out.println(SQL.auctionsSoonToFinish(localTimeInWantedStringFormat));
	}

	@Test
	void buyoutBoughtTest() {
		assertNotNull(SQL.buyoutBought("1", "test"));
	}
}