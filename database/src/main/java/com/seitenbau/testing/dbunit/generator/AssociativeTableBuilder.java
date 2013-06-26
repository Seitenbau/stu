package com.seitenbau.testing.dbunit.generator;

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
    final Table result = new AssociativeTable(name, javaName, getTableDescription(), columnBuilders);
    model.addTable(result);
    return result;
  }

}
