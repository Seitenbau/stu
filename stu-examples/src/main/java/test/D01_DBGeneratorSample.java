package test;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.example.DataSet;
import com.seitenbau.stu.database.DatabaseTester;
import com.seitenbau.stu.database.generator.DataType;
import com.seitenbau.stu.database.generator.DatabaseModel;
import com.seitenbau.stu.database.generator.data.DataGenerator;
import com.seitenbau.stu.database.generator.data.STUTableOutput;

public class D01_DBGeneratorSample
{
  public static void main(String[] args) throws Exception
  {
    DatabaseModel db = new DatabaseModel()
    {
      {
        database("Sonferenz");
        packageName("com.example");
      }
    };

    db.table("Users")
        .column("ID", DataType.INTEGER)
        .column("Name", DataType.VARCHAR)
        .build();
    // .. add more tables
    db.generate();
    
    // Write TestData Groovy DSL file 
    String dsl = new STUTableOutput().create(new DataGenerator(db).generate());
    String outFile = "src/test/generated-java/"+db.getPackageName().replace(".", "/") + "/"+ db.getName()+"DataSetDefault.groovy";
    System.out.println(outFile);
    FileUtils.write(new File(outFile), dsl);

  }
  
  @Test
  public void testCleanInsert() throws Exception {
    
    // Connect to DB
    DatabaseTester dbTester = new DatabaseTester(
        "org.gjt.mm.mysql.Driver",
        "jdbc:mysql://localhost:3306/stu",
        "root",
        ""
       );
    
    // prepare DB
    DataSet d = new DataSet();
    dbTester.cleanInsert(d);
  }
}