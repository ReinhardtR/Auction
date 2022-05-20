package server.persistence.utils.sqlcode;

import org.junit.jupiter.api.Test;
import server.persistence.utils.tables.Table;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SQLStatementsTest {
	SQLStatements statements = new SQLStatements();
	Table test = new Table("testSchema", "test");


	@Test
	void selectTest() {
		assertEquals("SELECT test, test FROM \"testSchema\".test WHERE test = true", statements.select(test, new String[]{"test", "test"}, "test = true"));
	}

	@Test
	void updatTest() {
		assertEquals("UPDATE \"testSchema\".test SET test = true WHERE test = false", statements.update(test, "test = true", "test = false"));
	}

	@Test
	void testDelete() {
		assertEquals("DELETE \"testSchema\".test WHERE test = true", statements.delete(test, "test = true"));
	}

	@Test
	void unionTest() {
		assertEquals("SELECT test, test FROM \"testSchema\".test WHERE test = true"
						+ " UNION " +
						"SELECT test, test FROM \"testSchema\".test WHERE test = true", statements.union(new Table[]{test, test}, new String[]{"test", "test"}, "test = true"));
	}

	@Test
	void coalesce() {
		assertEquals("SELECT COALESCE(test.test1, test.test1) as test1, " +
										"COALESCE(test.test2, test.test2) as test2, " +
										"COALESCE(test.test3, test.test3) as test3 " +
										"FROM \"testSchema\".test " +
										"FULL JOIN test on test.test1 = test.test1 " +
										"ORDER BY test1 " +
										"ASC FETCH FIRST 10 ROWS ONLY"
						, statements.selectCoalesce(new Table[]{test, test}, new String[]{"test1", "test2", "test3"}, "test1", "ASC", 10));
	}
}