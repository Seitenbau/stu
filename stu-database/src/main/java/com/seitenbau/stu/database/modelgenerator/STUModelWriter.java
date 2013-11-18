package com.seitenbau.stu.database.modelgenerator;

public class STUModelWriter
{

  public String generate(DatabaseModel model)
  {
    StringBuilder result = new StringBuilder();
    for (TableModel table : model.getTables())
    {
      if (table.isCreated())
      {
        continue;
      }
      result.append(table.createJavaSource());
    }

    return result.toString();
  }

}
