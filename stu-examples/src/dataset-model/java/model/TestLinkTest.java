package model;

import org.junit.Test;

import com.seitenbau.stu.database.DatabaseTester;
import com.seitenbau.stu.database.model.TestLinkDataSetDefault;


public class TestLinkTest {
	@Test
	public void testCleanInsert() throws Exception {
		// Connect to DB
		DatabaseTester dbTester = new DatabaseTester("org.gjt.mm.mysql.Driver", "jdbc:mysql://localhost:3306/testlink", "root", "root");
		// prepare DB
		TestLinkDataSetDefault d = new TestLinkDataSetDefault();
		dbTester.cleanInsert(d);
	}
}