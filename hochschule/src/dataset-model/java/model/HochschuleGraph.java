package model;

import com.seitenbau.testing.dbunit.generator.DatabaseModel;
import com.seitenbau.testing.dbunit.generator.data.DataGenerator;
import com.seitenbau.testing.dbunit.generator.data.EntityFactory;

public class HochschuleGraph
{

  public static void main(String[] args) throws Exception
  {
    EntityFactory fab = new EntityFactory();
    DatabaseModel model = new HochschuleModel();

    new DataGenerator(fab, model).generate();
  }

}
