package server.persistence.utils.sql_code;

import org.junit.jupiter.api.Test;
import server.persistence.utils.exceptions.NonExistentOperator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SQLOperationTest {
	SQLOperation operators = new SQLOperation();

	@Test
	void makeTest() {
		try {
			assertEquals("test < test", operators.make(new String[][]{{"test", "<", "test"}}));
			assertEquals("test < test , test < test", operators.make(new String[][]{{"test", "<", "test", ","}, {"test", "<", "test"}}));
		} catch (NonExistentOperator e) {
			e.printStackTrace();
		}
	}

	@Test
	void makeTestException() {
		assertThrows(NonExistentOperator.class, () -> operators.make(new String[][]{{"test", "test", "test"}}));
	}

}