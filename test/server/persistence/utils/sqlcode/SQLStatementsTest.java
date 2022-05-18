package server.persistence.utils.sqlcode;

import org.junit.jupiter.api.Test;
import server.persistence.utils.tables.Table;

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
		assertEquals("DELETE \"null\".null WHERE test = true", statements.delete(test, "test = true"));
	}

	@Test
	void unionTest() {
		assertEquals("SELECT test, test FROM \"null\".null WHERE test = true"
						+ " UNION " +
						"SELECT test, test FROM \"null\".null WHERE test = true", statements.union(new Table[]{test, test}, new String[]{"test", "test"}, "test = true"));
	}

	@Test
	void coalesce() {
		assertEquals("SELECT COALESCE(null.test1, null.test1) as test1, " +
										"COALESCE(null.test2, null.test2) as test2, " +
										"COALESCE(null.test3, null.test3) as test3 " +
										"FROM auction " +
										"FULL JOIN buyout on auction.itemid = buyout.itemid " +
										"ORDER BY test1 " +
										"ASC FETCH FIRST 10 ROWS ONLY"
						, statements.selectAmount(new Table[]{test, test}, new String[]{"test1", "test2", "test3"}, "test1", "ASC", 10));
	}
}