package server.softwarehouseacces.utils.tables;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.softwarehouseacces.utils.exceptions.ColumnNonExistent;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {
	Table auction;
	Table buyout;

	@BeforeEach
	void setUp() {
		auction = new AuctionTable();
		buyout = new BuyoutTable();
	}

	@Test
	@DisplayName("Check if all tables have the agreed upon schema location")
	void getSchemaTest() {
		assertEquals("public", auction.schemaName, "Should be true, if not check if schema name on database = " + auction.schemaName + " and update either AuctionTable or Test to represent");
		assertEquals("public", buyout.schemaName, "Should be true, if not check if schema name on database = " + buyout.schemaName + " and update either BuyoutTable or Test to represent");
	}

	@Test
	@DisplayName("Check if all tables have the agreed upon table name")
	void getTableTest() {
		assertEquals("auction", auction.tableName, "Should be true, if not check if table name on database = " + auction.tableName + " and update either AuctionTable or Test to represent");
		assertEquals("buyout", buyout.tableName, "Should be true, if not check if table name on database = " + buyout.tableName + " and update either BuyoutTable or Test to represent");
	}

	@Test
	@DisplayName("Check getColumn with AuctionTable as test subject")
	void getColumnTest() {
		assertAll("Checking Sunnyside for Table.getColumn",
						() -> assertEquals("currentbid", auction.getColumn("currentbid")),
						() -> assertEquals("currentbid", auction.getColumn("CurReNtbiD"), "Should work since method isn't case sensitive"),
						() -> assertEquals("currentbid", auction.getColumn("     currentbid         "), "Should work since string gets stripped")
		);
	}

	@Test
	@DisplayName("Check getColumn Throw of ColumnNonExistent with AuctionTable as test subject")
	void checkGetColumnThrowTest() {
		assertThrows(ColumnNonExistent.class, () -> auction.getColumn("test"), "Should throw since column is non existent");
	}

	@Test
	void getColumnsTest() {
		String[] aucSupposedColumns = {"currentbid", "currentbidder", "auctionenddate"};
		String[] buySupposedColumns = {"price", "buyer"};
		assertTrue(columnChecks(auction, aucSupposedColumns));
		assertTrue(columnChecks(buyout, buySupposedColumns));
	}

	private boolean columnChecks(Table tableToCheck, String[] supposedColumns) {
		for (String column : supposedColumns) {
			try {
				tableToCheck.getColumn(column);
			} catch (ColumnNonExistent e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
}