package com.seitenbau.stu.database.generator;

public class AssociativeTableBuilder extends TableBuilder
{

  AssociativeTableBuilder(DatabaseModel model, String name)
  {
    super(model, name);
  }

  /**
   * Finalizes the creation of the table.
   * @return The created table
   */
  @Override
  public Table build()
  {
    final Table result = new AssociativeTable(name, javaName, getTableDescription(), seed,
        infinite, minEntities, columnBuilders, constraintColumnPairs, dataSource);
    model.addTable(result);
    return result;
  }

}
