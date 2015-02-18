package model;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Test;


import com.seitenbau.stu.database.DatabaseTester;
import com.seitenbau.stu.database.generator.DataType;
import com.seitenbau.stu.database.generator.DatabaseModel;
import com.seitenbau.stu.database.generator.data.DataGenerator;
import com.seitenbau.stu.database.generator.data.Entities;
import com.seitenbau.stu.database.generator.data.STUTableOutput;

public class D01_DBGeneratorSample {
	public static void main(String[] args) throws Exception {
		DatabaseModel db = new DatabaseModel() {
			{
				database("Sonferenz");
				packageName("com.example");
			}
		};
		db.table("Users").column("ID", DataType.INTEGER).column("Name", DataType.VARCHAR).build();
		// .. add more tables
		db.generate();
		// Write TestData Groovy DSL file
		Entities entities = new DataGenerator(db).generate();
		String dsl = new STUTableOutput().create(entities);
		String outFile = "src/test/generated-java/" + db.getPackageName().replace(".", "/") + "/" + db.getName() + "DataSetDefault.groovy";
		System.out.println(outFile);
		FileUtils.write(new File(outFile), dsl);
		
		String refs = new STUTableOutput().createRefs(entities);
		String outFileRefs = "src/test/generated-java/" + db.getPackageName().replace(".", "/") + "/" + db.getName() + "DataSetRefs.java";
		System.out.println(outFileRefs);
		FileUtils.write(new File(outFileRefs), refs);
	}

	@Test
	public void testCleanInsert() throws Exception {
		// Connect to DB
		DatabaseTester dbTester = new DatabaseTester("org.gjt.mm.mysql.Driver", "jdbc:mysql://localhost:3306/stu", "root", "root");
		// prepare DB
//		DataSet d = new DataSet();
//		dbTester.cleanInsert(d);
	}
}