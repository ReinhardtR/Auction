package server.persistence.utils.sql_code;

import org.junit.jupiter.api.Test;
import server.persistence.utils.exceptions.SQLUtilsException;
import server.persistence.utils.tables.Table;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SQLStatementsTest {
	SQLStatements statements = new SQLStatements();
	Table test = new Table("testSchema", "test");


	@Test
	void selectTest() {
		assertEquals("SELECT test, test FROM \"testSchema\".test WHERE test = true", statements.select(test, new String[]{"test", "test"}, "test = true"));
	}

	@Test
	void updateTest() {
		assertEquals("UPDATE \"testSchema\".test SET test = true WHERE test = false", statements.update(test, "test = true", "test = false"));
	}

	@Test
	void testDelete() {
		assertEquals("DELETE FROM \"testSchema\".test WHERE test = true", statements.delete(test, "test = true"));
	}

	@Test
	void unionTest() {
		assertEquals("SELECT test, test FROM \"testSchema\".test WHERE test = true"
						+ " UNION " +
						"SELECT test, test FROM \"testSchema\".test WHERE test = true", statements.union(new Table[]{test, test}, new String[]{"test", "test"}, "test = true"));
	}

	@Test
	void selectCoalesceTest() throws SQLUtilsException {
		assertEquals("SELECT COALESCE(test.test1, test.test1) as test1, " +
										"COALESCE(test.test2, test.test2) as test2, " +
										"COALESCE(test.test3, test.test3) as test3 " +
										"FROM \"testSchema\".test " +
										"FULL JOIN test on test.test1 = test.test1 " +
										"ORDER BY test1 " +
										"ASC FETCH FIRST 10 ROWS ONLY"
						, statements.selectCoalesce(new Table[]{test, test}, new String[]{"test1", "test2", "test3"}, "test1", "ASC", 10));
	}

	@Test
	void coalesceThrowsTest() {
		assertThrows(SQLUtilsException.class, () -> statements.coalesce(new Table[]{test}, "test"));
	}

	@Test
	void fullJoinThrowsTest() {
		assertThrows(SQLUtilsException.class, () -> statements.fullJoin(new Table[]{test}, "test"));
	}

	@Test
	void orderByThrowsTest() {
		assertThrows(SQLUtilsException.class, () -> statements.orderBy("test", "test"));
	}

	@Test
	void insertTest() {
		assertEquals("INSERT INTO \"testSchema\".test " +
										"VALUES (test)"
						, statements.insert(test, "test"));
	}
}