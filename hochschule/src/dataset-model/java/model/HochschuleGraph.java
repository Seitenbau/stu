package model;

import java.util.HashSet;
import java.util.Set;

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

    Set<Integer> hashes = new HashSet<Integer>();

    DataGenerator generator = new DataGenerator(model);

    StringBuilder summary = new StringBuilder();
    for (Table table : model.getTables()) {

      Entities entities = generator.generate(table);

      STUTableOutput output = new STUTableOutput();
      final String generatedDSL = output.create(entities);
      int hash = generatedDSL.hashCode();

      if (!hashes.contains(hash)) {
        hashes.add(hash);
        System.out.println("Start Table: " + table.getJavaName());
        System.out.println(generatedDSL);
        entities.printStats();
        System.out.println();
      }

      summary.append("generated DSL-Hash: " + hash + "\t");
      summary.append("Loop Count: " + entities.getLoopCount() + "\t");
      summary.append("Start Table: " + table.getJavaName() + "\n");
    }

    System.out.println(summary.toString());
  }

}