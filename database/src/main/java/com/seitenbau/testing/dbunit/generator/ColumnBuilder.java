package com.seitenbau.testing.dbunit.generator;

import java.util.ArrayList;
import java.util.List;

public class ColumnBuilder
{
  private final TableBuilder tableBuilder;

  private final String name;

  private final DataType dataType;

  private List<Column> references = new ArrayList<Column>();

  private boolean enableAutoIdHandling;

  private boolean isIdentifierColumn;

  public ColumnBuilder(TableBuilder tableBuilder, String name, DataType dataType)
  {
    this.tableBuilder = tableBuilder;
    this.name = name;
    this.dataType = dataType;
    this.enableAutoIdHandling = false;
    this.isIdentifierColumn = false;
  }

  public Table build()
  {
    buildColumn();
    return tableBuilder.build();
  }

  public ColumnBuilder column(String name, DataType dataType)
  {
    buildColumn();
    return new ColumnBuilder(tableBuilder, name, dataType);
  }

  public ColumnBuilder autoIdHandling()
  {
    this.enableAutoIdHandling = true;
    return this;
  }

  public ColumnBuilder identifierColumn()
  {
    this.isIdentifierColumn = true;
    return this;
  }

  public ColumnBuilder references(Column reference)
  {
    this.references.add(reference);
    return this;
  }

  private void buildColumn()
  {
    Table parentTable = tableBuilder.build();
    Column[] references = getReferencesAsArray();
    Flags[] flags = determineFlags();
    Column column = new Column(parentTable, name, null, dataType.getDataType(), dataType.getJavaType(), references,
        flags);

    tableBuilder.addColumn(column);
  }

  private Column[] getReferencesAsArray()
  {
    return references.toArray(new Column[] {});
  }

  private Flags[] determineFlags()
  {
    List<Flags> flags = new ArrayList<Flags>();

    if (isIdentifierColumn)
    {
      flags.add(Flags.AutoIncrement);
    }

    if (enableAutoIdHandling)
    {
      flags.add(Flags.AutoInvokeNextIdMethod);
    }

    // TODO Handling for missing Flags

    return flags.toArray(new Flags[] {});
  }

}
