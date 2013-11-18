package com.seitenbau.stu.database.modelgenerator;

import java.util.ArrayList;
import java.util.List;

public class DatabaseModel
{

  private final List<TableModel> tables;

  public DatabaseModel()
  {
    tables = new ArrayList<TableModel>();
  }

  public List<TableModel> getTables()
  {
    return tables;
  }

  public void add(TableModel tableModel)
  {
    tables.add(tableModel);
  }

}
