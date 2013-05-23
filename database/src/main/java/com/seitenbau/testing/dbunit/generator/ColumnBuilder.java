package com.seitenbau.testing.dbunit.generator;

import java.util.ArrayList;
import java.util.List;

import com.seitenbau.testing.util.CamelCase;

public class ColumnBuilder
{
  private final TableBuilder tableBuilder;

  private final String name;

  private final DataType dataType;

  private List<Reference> references = new ArrayList<Reference>();

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

  public ReferenceBuilder references(Column reference)
  {
    return new ReferenceBuilder(this, reference);
  }

  public ReferenceBuilder references(Table reference)
  {
    return new ReferenceBuilder(this, reference.getIdColumn());
  }
  
  void addReference(Reference reference) 
  {
    references.add(reference);
  }
  
  private void buildColumn()
  {
    Table parentTable = tableBuilder.build();
    Reference[] references = getReferencesAsArray();
    Flags[] flags = determineFlags();
    Column column = new Column(parentTable, name, null, dataType.getDataType(), dataType.getJavaType(), references,
        flags);

    tableBuilder.addColumn(column);
  }

  private Reference[] getReferencesAsArray()
  {
    return references.toArray(new Reference[] {});
  }

  private Flags[] determineFlags()
  {
    List<Flags> flags = new ArrayList<Flags>();

    if (isIdentifierColumn)
    {
      flags.add(Flags.IdentifierColumn);
    }

    if (enableAutoIdHandling)
    {
      flags.add(Flags.AutoInvokeNextIdMethod);
    }

    // TODO Handling for missing Flags

    return flags.toArray(new Flags[] {});
  }

  String getColumnName()
  {
    return name;
  }
  
  TableBuilder getTableBuilder()
  {
    return tableBuilder;
  }

  public String getColumnJavaName()
  {
    String javaName = CamelCase.makeFirstOfBlockUppercase(name);
    return CamelCase.makeFirstUpperCase(javaName);
  }
}
