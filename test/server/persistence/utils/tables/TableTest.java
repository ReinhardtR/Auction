package server.persistence.utils.tables;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.persistence.utils.exceptions.ColumnNonExistent;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {
	Table test1;
	Table test2;

	@BeforeEach
	void setUp() {
		test1 = new Table("test1Schema", "test1");
		test2 = new Table("test2Schema", "test2");
		test1.addColumn("test");
		test2.addColumn("test");
	}

	@Test
	@DisplayName("Check getColumn with AuctionTable as test subject")
	void getColumnTest() {
		assertAll("Checking Sunnyside for Table.getColumn",
						() -> assertEquals("test", test1.getColumn("test")),
						() -> assertEquals("test", test1.getColumn("TeSt"), "Should work since method isn't case sensitive"),
						() -> assertEquals("test", test1.getColumn("     test         "), "Should work since string gets stripped")
		);
	}

	@Test
	@DisplayName("Check getColumn Throw of ColumnNonExistent with AuctionTable as test subject")
	void checkGetColumnThrowTest() {
		assertThrows(ColumnNonExistent.class, () -> test1.getColumn("testt"), "Should throw since column is non existent");
	}

	@Test
	void getColumnsTest() {
		String[] test1SupposedColumns = {"test"};
		assertTrue(columnChecks(test1, test1SupposedColumns));
		test1SupposedColumns = new String[]{"test", "test1", "test2"};
		test1.addColumn("test1");
		test1.addColumn("test2");
		assertTrue(columnChecks(test1, test1SupposedColumns));
	}

	private boolean columnChecks(Table tableToCheck, String[] supposedColumns) {
		for (String column : supposedColumns) {
			try {
				tableToCheck.getColumn(column);
			} catch (ColumnNonExistent e) {
				return false;
			}
		}
		return true;
	}

	@Test
	void getCommonColumnsTest() throws ColumnNonExistent {
		assertEquals("test", test1.getCommonColumns(test2)[0]);
		test1.addColumn("test1");
		test2.addColumn("test2");
		assertEquals(1, test1.getCommonColumns(test2).length);
		test2.addColumn("test1");
		assertEquals(2, test1.getCommonColumns(test2).length);
	}

	@Test
	void getCommonColumnsThrowsTest() {
		Table noCommonColumns = new Table(null, null);
		noCommonColumns.addColumn("notSameCollumn");
		assertThrows(ColumnNonExistent.class, () -> test1.getCommonColumns(noCommonColumns), "Should throw since column is non existent");
	}
}