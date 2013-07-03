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

  private String tableName;

  private String description;

  private final Set<String> flags;

  /**
   * Allows to configure a reference to another column.
   * The configuration is split into a local and a foreign part. The local part
   * describes the relation from the perspective of the current table. The
   * foreign part specifies the column and describes the relation from the
   * perspective of the referenced table.
   * <p>
   * The following example models the relation between an house and its rooms:
   * <code><pre>
   * Table house = table("house")
   *     .column("id", DataType.BIGINT)
   *       .identifierColumn()
   *     .column("address", DataType.VARCHAR)
   *   .build();
   *
   * table("room")
   *     .column("id", DataType.BIGINT)
   *     .column("house", DataType.BIGINT)
   *       .reference
   *         .local
   *           .name("belongsTo")
   *         .foreign(house)
   *           .name("hasRoom")
   *   .build();
   * </pre></code>
   *
   * Associative n:m relations should be modeled by association tables (see
   * {@link DatabaseModel#associativeTable}).
   */
  public final ColumnReferenceBuilder reference;

  public ColumnBuilder(TableBuilder tableBuilder, String name, DataType dataType)
  {
    this.flags = new HashSet<String>();
    this.tableBuilder = tableBuilder;
    this.name = name;
    this.description = null;
    this.javaName = null;
    this.tableName = null;
    this.dataType = dataType;

    this.reference = new ColumnReferenceBuilder(this);
    tableBuilder.addColumnBuilder(this);
  }

  /**
   * Finalizes the creation of the table.
   * @return The created table
   */
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

    String p_tableName = tableName;
    if (p_tableName == null)
    {
      p_tableName = name.toLowerCase();
    }

    return new Column(table, name, p_javaName, p_tableName, description, dataType, reference.getReference(),
        flags);
  }

  /**
   * Adds a further column to the table.
   * @param name The database name of the column.
   * @param dataType The column's data type.
   * @return The column builder
   */
  public ColumnBuilder column(String name, DataType dataType)
  {
    return new ColumnBuilder(tableBuilder, name, dataType);
  }

  /**
   * Defines how the column is spelled in the Java source code.
   * @param javaName The column name within the Java sources
   * @return The column builder
   */
  public ColumnBuilder javaName(String javaName)
  {
    this.javaName = javaName;
    return this;
  }

  /**
   * Defines how the column is spelled in the Groovy Table Builder source codes.
   * @param javaName The column name within the Groovy sources
   *
   * @return The builder to configure the column
   */
  public ColumnBuilder tableName(String tableName)
  {
    this.tableName = tableName;
    return this;
  }

  /**
   * Adds a description to the current column. This description is used for JavaDoc comments
   * in the generated classed
   * @param description The column description
   * @return The column builder
   */
  public ColumnBuilder description(String description)
  {
    this.description = description;
    return this;
  }

  /**
   * The the corresponding {@code nextValue()} method is automatically
   * called on insertRow. Enables the flag {@link ColumnMetaData#ADD_NEXT_METHOD} and
   * therefore the generation of a next value method as well.
   * <p>
   * Note: When used in combination with {@link ColumnMetaData#IMMUTABLE}, the automatic
   * generated value can be overwritten once.
   *
   * @return The column builder
   */
  public ColumnBuilder autoInvokeNext()
  {
    setFlag(ColumnMetaData.AUTO_INVOKE_NEXT);
    return setFlag(ColumnMetaData.ADD_NEXT_METHOD);
  }

  /**
   * Enables the DBUnit AutoIncrement flag. Useful e.g., if a {@link DatabaseTesterCleanAction}
   * requires the information. Enables the flag {@link ColumnMetaData#ADD_NEXT_METHOD} and
   * therefore the generation of a next value method as well.
   * <p>
   * <b>This flag requires DbUnit 2.4.3 or later</b>
   *
   * @return The column builder
   */
  public ColumnBuilder autoIncrement()
  {
    setFlag(ColumnMetaData.AUTO_INCREMENT);
    return setFlag(ColumnMetaData.ADD_NEXT_METHOD);
  }

  /**
   * Enables the generation of a next value method (depending on the column name)
   * on the RowBuilder. The next value method will use the {@link DatasetIdGenerator}
   * from the DataSetModel.
   * @return The builder to configure the column
   *
   * // TODO NM translate comment
   * Auﬂerdem werden in der Builder Klasse des erzeugten DataSets bei einem create*() die
   * nextMethoden automatisch gerufen.
   */
  public ColumnBuilder addNextMethod()
  {
    return setFlag(ColumnMetaData.ADD_NEXT_METHOD);
  }

  /**
   * The column is the default identification column for the table when used to build
   * a relation to the table. Implicitly activates the Flags {@link ColumnMetaData#UNIQUE}
   * and {@link ColumnMetaData#IMMUTABLE} as well.
   * <p>
   * To enable automatic value generation, the flag {@link ColumnMetaData#AUTO_INVOKE_NEXT}
   * has to be enabled.
   *
   * @return The column builder
   */
  public ColumnBuilder identifierColumn()
  {
    setFlag(ColumnMetaData.IDENTIFIER);
    setFlag(ColumnMetaData.UNIQUE);
    return setFlag(ColumnMetaData.IMMUTABLE);
  }


  /**
   * The column value is unique and so it can be used to identify the entity (the whole table row).
   * Although a database primary key column is an identifier column, there is no need for an unique
   * column to be explicit a primary key.
   * <p>
   * Implicitly activates the flag {@link ColumnMetaData#IMMUTABLE}.
   * <p>
   * The value of an unique column has to be set when a row is created (manually or
   * automatically). It cannot be changed afterwards. Use autoInvokeNext() for automatic
   * value generation.
   * An unique value column cannot contain lazy evaluated Future values.
   *
   * @return The column builder
   */
  public ColumnBuilder unique()
  {
    setFlag(ColumnMetaData.UNIQUE);
    return setFlag(ColumnMetaData.IMMUTABLE);
  }

  /**
   * Ensures that the value of the column can only be set once.
   * <p>
   * Note: When used in combination with {@link ColumnMetaData#AUTO_INVOKE_NEXT}, the automatic
   * generated value can be overwritten once.
   *
   * @return The column builder
   */
  public ColumnBuilder immutable()
  {
    return setFlag(ColumnMetaData.IMMUTABLE);
  }

  public ColumnBuilder setFlag(String flag)
  {
    flags.add(flag);
    return this;
  }

// TODO NM CLEAR ---------
  /**
   * Models a relation to another table column. A relation will automatically add methods
   * to model relations (e.g. setters on RowBuilders and methods on the Ref classes).
   * <p>
   * Although relations are mostly achieved by public key and foreign key columns in the
   * actual database, a relation in STU does not require this.
   * @param targetColumn The column, which the current column is related to
   * @return The builder to configure the relation
   */

  /**
   * Models a relation to another table using its identifier column. A relation will automatically
   * add methods to model relations (e.g. setters on RowBuilders and methods on the Ref classes).
   * <p>
   * Although relations are mostly achieved by public key and foreign key columns in the
   * actual database, a relation in STU does not require this.
   * @param targetColumn The column, which the current column is related to
   * @return The builder to configure the relation
   */
// CLEAR -----------------

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
