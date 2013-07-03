package com.seitenbau.testing.dbunit.generator;

import com.seitenbau.testing.util.CamelCase;

public class ColumnReferenceBuilder
{
  private final ColumnBuilder columnBuilder;

  private Column foreignColumn;

  /**
   * TODO NM UPDATE
   * Describes the relation regarding the entities in the current table. The generated Ref class
   * belonging to the current table will have a corresponding method to model this relation.
   * <p>
   * When modeling associative tables, use {@link ColumnReferenceBuilder#target(String)} to define the
   * generated methods on the involved Ref classes.
   * <p>
   * Example: A player belongs to a team
   * <pre>
   * table("player")
   *     .column("team_id", DataType.BIGINT)
   *       .references
   *         .local
   *           .name("belongsTo")
   * </pre>
   * @return The builder to configure the local relation.
   */
  public final LocalReferenceBuilder local;

  private ForeignReferenceBuilder foreign;

  private String localName;

  private String localDescription;

  private String localMultiplicities;

  private String foreignName;

  private String foreignDescription;

  private String foreignMultiplicities;

  public ColumnReferenceBuilder(ColumnBuilder columnBuilder)
  {
    this.columnBuilder = columnBuilder;

    local = new LocalReferenceBuilder(this);

    // initialize values
    localName = CamelCase.makeFirstLowerCase(columnBuilder.getColumnJavaName()) + "To";
    localDescription = "";
    localMultiplicities = "1";

    foreignName = CamelCase.makeFirstLowerCase(columnBuilder.getTableBuilder().getJavaName()) + "to";
    foreignDescription = "";
    foreignMultiplicities = "1";
  }

  /**
   * TODO NM UPDATE
   * Describes the relation regarding the entities in the target table. The generated Ref class
   * belonging to the target table will have a corresponding method to model this relation.
   * <p>
   * When modeling associative tables, only the target names will be used as method names
   * for the involved Ref classes (see Example 2).
   * <p>
   * Example 1: Players are members of a team
   * <pre>
   * table("player")
   *     .column("team_id", DataType.BIGINT)
   *       .relationTo(teamTable)
   *         .target("hasMembers")
   * </pre>
   * <p>
   * Example 2: Associative Table<br>
   * Any person can be member of any group.
   * <pre>
   * table("person_group")
   *       .column("person_id", DataType.BIGINT)
   *         .relationTo(person)
   *           .target("belongsTo")
   *       .column("group_id", DataType.BIGINT)
   *         .relationTo(group)
   *           .target("hasMembers")
   * </pre>
   * @param name The relation name used for the generated DSL.
   * @return The builder to configure the target relation.
   */
  public ForeignReferenceBuilder foreign(Column foreignColumn)
  {
    if (foreign != null)
    {
      throw new IllegalArgumentException("Multiple definition of target not allowed");
    }
    this.foreignColumn = foreignColumn;
    foreign = new ForeignReferenceBuilder(this);
    return foreign;
  }

  /**
   * TODO NM add
   */
  public ForeignReferenceBuilder foreign(Table table)
  {
    return foreign(table.getIdentifierColumn());
  }

  /**
   * Adds a further column to the table.
   * @param name The database name of the column.
   * @param dataType The column's data type.
   * @return The column builder
   */
  public ColumnBuilder column(String name, DataType dataType)
  {
    buildReference();
    return columnBuilder.column(name, dataType);
  }

  /**
   * Finalizes the creation of the table.
   * @return The created table
   */
  public Table build()
  {
    buildReference();
    return columnBuilder.build();
  }

  private void buildReference()
  {
    columnBuilder.setRelation(new ColumnReference(foreignColumn,
        localName, localDescription, localMultiplicities,
        foreignName, foreignDescription, foreignMultiplicities));
  }

  public static class LocalReferenceBuilder
  {

    private final ColumnReferenceBuilder parent;

    public LocalReferenceBuilder(ColumnReferenceBuilder parent)
    {
      this.parent = parent;
    }

    public LocalReferenceBuilder name(String name)
    {
      parent.localName = name;
      return this;
    }

    public LocalReferenceBuilder description(String description)
    {
      parent.localDescription = description;
      return this;
    }

    public LocalReferenceBuilder multiplicity(String multiplicities)
    {
      parent.localMultiplicities = multiplicities;
      return this;
    }

    /**
     * TODO NM UPDATE
     * Describes the relation regarding the entities in the target table. The generated Ref class
     * belonging to the target table will have a corresponding method to model this relation.
     * <p>
     * When modeling associative tables, only the target names will be used as method names
     * for the involved Ref classes (see Example 2).
     * <p>
     * Example 1: Players are members of a team
     * <pre>
     * table("player")
     *     .column("team_id", DataType.BIGINT)
     *       .relationTo(teamTable)
     *         .target("hasMembers")
     * </pre>
     * <p>
     * Example 2: Associative Table<br>
     * Any person can be member of any group.
     * <pre>
     * table("person_group")
     *       .column("person_id", DataType.BIGINT)
     *         .relationTo(person)
     *           .target("belongsTo")
     *       .column("group_id", DataType.BIGINT)
     *         .relationTo(group)
     *           .target("hasMembers")
     * </pre>
     * @param name The relation name used for the generated DSL.
     * @return The builder to configure the target relation.
     */
    public ForeignReferenceBuilder foreign(Column foreignColumn)
    {
      return parent.foreign(foreignColumn);
    }

    /**
     * TODO NM add
     */
    public ForeignReferenceBuilder foreign(Table table)
    {
      return parent.foreign(table.getIdentifierColumn());
    }

    /**
     * Adds a further column to the table.
     * @param name The database name of the column.
     * @param dataType The column's data type.
     * @return The column builder
     */
    public ColumnBuilder column(String name, DataType dataType)
    {
      return parent.column(name, dataType);
    }

    /**
     * Finalizes the creation of the table.
     * @return The created table
     */
    public Table build()
    {
      return parent.build();
    }

  }

  public static class ForeignReferenceBuilder
  {
    private final ColumnReferenceBuilder parent;

    /**
     * TODO NM UPDATE
     * Describes the relation regarding the entities in the current table. The generated Ref class
     * belonging to the current table will have a corresponding method to model this relation.
     * <p>
     * When modeling associative tables, use {@link ColumnReferenceBuilder#target(String)} to define the
     * generated methods on the involved Ref classes.
     * <p>
     * Example: A player belongs to a team
     * <pre>
     * table("player")
     *     .column("team_id", DataType.BIGINT)
     *       .relationTo(teamTable)
     *         .local("belongsTo")
     * </pre>
     * @param name The relation name used for the generated DSL.
     * @return The builder to configure the local relation.
     */
    public final LocalReferenceBuilder local;

    public ForeignReferenceBuilder(ColumnReferenceBuilder parent)
    {
      this.parent = parent;
      local = parent.local;
    }

    public ForeignReferenceBuilder name(String name)
    {
      parent.foreignName = name;
      return this;
    }

    public ForeignReferenceBuilder description(String description)
    {
      parent.foreignDescription = description;
      return this;
    }

    public ForeignReferenceBuilder multiplicity(String multiplicities)
    {
      parent.foreignMultiplicities = multiplicities;
      return this;
    }

    /**
     * Adds a further column to the table.
     * @param name The database name of the column.
     * @param dataType The column's data type.
     * @return The column builder
     */
    public ColumnBuilder column(String name, DataType dataType)
    {
      return parent.column(name, dataType);
    }

    /**
     * Finalizes the creation of the table.
     * @return The created table
     */
    public Table build()
    {
      return parent.build();
    }

  }

}
