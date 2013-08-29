package model;

import com.seitenbau.testing.dbunit.generator.DatabaseModel;
import com.seitenbau.testing.dbunit.generator.EntityFactory;
import com.seitenbau.testing.dbunit.generator.data.DataGenerator;

public class HochschuleGraph
{

  public static void main(String[] args) throws Exception
  {
    EntityFactory fab = new EntityFactory();
    DatabaseModel model = new HochschuleModel();

    new DataGenerator(fab, model).generate();
  }

}
