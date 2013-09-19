package model;

import com.seitenbau.testing.dbunit.generator.DatabaseModel;
import com.seitenbau.testing.dbunit.generator.Table;
import com.seitenbau.testing.dbunit.generator.data.DataGenerator;
import com.seitenbau.testing.dbunit.generator.data.Entities;
import com.seitenbau.testing.dbunit.generator.data.STUTableOutput;

public class HochschuleGraph
{

  public static void main(String[] args) throws Exception
  {
    DatabaseModel model = new HochschuleModel();

    boolean isFirst = true;

    DataGenerator generator = new DataGenerator(model);
    for (Table table : model.getTables()) {

      Entities entities = generator.generate(table);

      STUTableOutput output = new STUTableOutput();
      final String generatedDSL = output.create(entities);
      if (isFirst) {
        System.out.println("Start Table: " + table.getJavaName());
        System.out.println(generatedDSL);
        entities.printStats();
        System.out.println();
        isFirst = false;
      }
      System.out.print("generated DSL-Hash: " + generatedDSL.hashCode() + "\t");
      System.out.print("Loop Count: " + entities.getLoopCount() + "\t");
      System.out.println("Start Table: " + table.getJavaName());
    }

  }

}
