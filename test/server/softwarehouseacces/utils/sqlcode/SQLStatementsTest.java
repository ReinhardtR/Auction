package server.softwarehouseacces.utils.sqlcode;

import org.junit.jupiter.api.Test;
import server.softwarehouseacces.utils.tables.Table;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SQLStatementsTest {
	SQLStatements statements = new SQLStatements();
	Table test = new Table() {
	};

	@Test
	void selectTest() {
		assertEquals("SELECT test, test FROM \"null\".null WHERE test = true", statements.select(test, new String[]{"test", "test"}, "test = true"));
	}

	@Test
	void updatTest() {
		assertEquals("UPDATE \"null\".null SET test = true WHERE test = false", statements.update(test, "test = true", "test = false"));
	}

	@Test
	void testDelete() {
		assertEquals("DELETE \"null\".null WHERE test = false", statements.delete(test, "test = true"));
	}

	@Test
	void unionTest() {
		assertEquals("SELECT test, test FROM \"null\".null WHERE test = true"
						+ " UNION " +
						"SELECT test, test FROM \"null\".null WHERE test = true", statements.union(new Table[]{test, test}, new String[]{"test", "test"}, "test = true"));
	}

}