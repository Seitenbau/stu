package model;

import com.seitenbau.stu.config.TestConfiguration;
import com.seitenbau.stu.database.generator.DatabaseModel;
import com.seitenbau.stu.database.generator.data.DataGenerator;
import com.seitenbau.stu.database.generator.data.DataGenerator.Mode;
import com.seitenbau.stu.database.generator.data.Entities;
import com.seitenbau.stu.database.generator.data.STUTableOutput;

public class BockDatabaseDataGenerator
{

  public static void main(String[] args) throws Exception
  {
//	TestConfiguration.load(Object.class);

    final DatabaseModel model = new BookDatabaseModel();

    final DataGenerator generator = new DataGenerator(model);

    final Entities entities = generator.generate("book");

    STUTableOutput output = new STUTableOutput();
    final String generatedDSL = output.create(entities);
    System.out.println(generatedDSL);

    System.out.println("-----------------------------");
    entities.printStats();
    System.out.println();

    System.out.println("Verification Loop Iterations: " + entities.getLoopCount() + "\t");
  }

}
