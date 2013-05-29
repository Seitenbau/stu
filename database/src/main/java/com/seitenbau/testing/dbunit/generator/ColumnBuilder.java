package com.seitenbau.testing.dbunit.generator;

import com.seitenbau.testing.dbunit.extend.DatabaseTesterCleanAction;
import com.seitenbau.testing.dbunit.extend.DatasetIdGenerator;
import com.seitenbau.testing.util.CamelCase;

public class ColumnBuilder
{
  private final TableBuilder tableBuilder;

  private final String name;

  private final DataType dataType;

  private String javaName;

  private String description;

  private Reference reference;

  private boolean enableAutoIdHandling;

  private boolean isAutoIncrementColumn;

  private boolean isIdentifierColumn;

  private boolean addNextMethod;

  public ColumnBuilder(TableBuilder tableBuilder, String name, DataType dataType)
  {
    this.tableBuilder = tableBuilder;
    this.name = name;
    this.description = null;
    this.javaName = null;
    this.dataType = dataType;
    this.enableAutoIdHandling = false;
    this.isIdentifierColumn = false;
    this.isIdentifierColumn = false;
    this.addNextMethod = false;

    tableBuilder.addColumnBuilder(this);
  }

  public Table build()
  {
    return tableBuilder.build();
  }

  Column buildColumn(Table table)
  {
    String p_javaName = javaName;
    if (p_javaName == null)
    {
      p_javaName = CamelCase.makeFirstOfBlockUppercase(name);
    }
    p_javaName = CamelCase.makeFirstUpperCase(p_javaName);
    return new Column(table, name, p_javaName, description, dataType.getDataType(), dataType.getJavaType(), reference,
        isIdentifierColumn, isAutoIncrementColumn, addNextMethod, enableAutoIdHandling);
  }

  public ColumnBuilder column(String name, DataType dataType)
  {
    return new ColumnBuilder(tableBuilder, name, dataType);
  }

  public ColumnBuilder javaName(String javaName)
  {
    this.javaName = javaName;
    return this;
  }
  
  public ColumnBuilder description(String description)
  {
    this.description = description;
    return this;
  }

  public ColumnBuilder autoIdHandling()
  {
    this.enableAutoIdHandling = true;
    return addNextMethod();
  }

  /**
   * Erzeugt in der DBUnit Spalte eine AutoIncrement Flag. Sinnvoll
   * falls Beispielsweise ein {@link DatabaseTesterCleanAction} diese
   * Information benötigt. Gleichzeitig wird aber auch eine nextId()
   * Methode für das Feld generiert. Gleich wie beim AddNextIdMethod.
   */
  public ColumnBuilder autoIncrement()
  {
    this.isAutoIncrementColumn = true;
    return addNextMethod();
  }

  /**
   * Erzeugt zusätzlich zu den setter Methoden der Spalte noch eine
   * nextId Methode (Wobei Id = Spaltenname). Diese Methode erzeugt
   * über einen {@link DatasetIdGenerator} aus dem DataSetModel beim
   * Aufruf die nächste ID. Außerdem werden in der Builder Klasse des
   * erzeugten DataSets bei einem create*() die nextMethoden
   * automatisch gerufen.
   */
  public ColumnBuilder addNextMethod()
  {
    this.addNextMethod = true;
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
    return new ReferenceBuilder(this, reference.getIdentifierColumn());
  }

  void addReference(Reference reference)
  {
    if (reference != null)
    {
      // TODO NM/CB exception?
    }
    this.reference = reference;
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
