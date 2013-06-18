package com.seitenbau.testing.dbunit.generator;

import java.util.HashSet;
import java.util.Set;

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

  private Relation relation;

  private final Set<String> flags;

  public ColumnBuilder(TableBuilder tableBuilder, String name, DataType dataType)
  {
    this.flags = new HashSet<String>();
    this.tableBuilder = tableBuilder;
    this.name = name;
    this.description = null;
    this.javaName = null;
    this.dataType = dataType;

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
    return new Column(table, name, p_javaName, description, dataType.getDataType(), dataType.getJavaType(), relation,
        flags);
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

  public ColumnBuilder autoInvokeNext()
  {
    setFlag(ColumnMetaData.AUTO_INVOKE_NEXT);
    return addNextMethod();
  }

  /**
   * Enables the DBUnit AutoIncrement flag. Useful e.g., if a {@link DatabaseTesterCleanAction}
   * requires the information. Enables the flag {@link ColumnMetaData#ADD_NEXT_METHOD} and
   * therefore the generation of a next value method as well.
   * <b>This flag requires DbUnit 2.4.3 or later</b>
   */
  public ColumnBuilder autoIncrement()
  {
    setFlag(ColumnMetaData.AUTO_INCREMENT);
    return addNextMethod();
  }

  /**
   * Enables the generation of a next value method (depending on the column name)
   * on the RowBuilder. The next value method will use the {@link DatasetIdGenerator}
   * from the DataSetModel.
   *
   * // TODO NM translate comment
   * Auﬂerdem werden in der Builder Klasse des erzeugten DataSets bei einem create*() die
   * nextMethoden automatisch gerufen.
   */
  public ColumnBuilder addNextMethod()
  {
    return setFlag(ColumnMetaData.ADD_NEXT_METHOD);
  }

  public ColumnBuilder identifierColumn()
  {
    return setFlag(ColumnMetaData.IDENTIFIER);
  }

  public ColumnBuilder setFlag(String flag)
  {
    flags.add(flag);
    return this;
  }

  public RelationBuilder relationTo(Column targetColumn)
  {
    return new RelationBuilder(this, targetColumn);
  }

  public RelationBuilder relationTo(Table table)
  {
    return new RelationBuilder(this, table.getIdentifierColumn());
  }

  void addRelation(Relation relation)
  {
    if (relation != null)
    {
      // Relation is overwritten...
      // TODO NM/CB exception?
    }
    this.relation = relation;
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
