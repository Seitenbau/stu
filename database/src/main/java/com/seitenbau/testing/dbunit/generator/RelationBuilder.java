package com.seitenbau.testing.dbunit.generator;

import com.seitenbau.testing.util.CamelCase;

public class RelationBuilder
{
  private final ColumnBuilder columnBuilder;

  private final Column column;

  private LocalReferenceBuilder local;

  private TargetReferenceBuilder target;

  private String localName;

  private String localDescription;

  private String targetName;

  private String targetDescription;

  private Integer targetMin;

  private Integer targetMax;

  public RelationBuilder(ColumnBuilder columnBuilder, Column reference)
  {
    this.columnBuilder = columnBuilder;
    this.column = reference;
  }

  public RelationBuilder description(String description)
  {
    targetDescription = description;
    return this;
  }

  /**
   * Describes the relation regarding the entities in the current table. The Refs belonging the
   * current table will have a corresponding method to model this kind of relations.
   * <p>
   * When modeling associative tables, use {@link RelationBuilder#target(String)} to define the
   * DSL features.
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
  public LocalReferenceBuilder local(String name)
  {
    if (local != null)
    {
      throw new IllegalArgumentException("Multiple definition of local not allowed");
    }
    local = new LocalReferenceBuilder(this, name);
    return local;
  }

  /**
   * Describes the relation regarding the entities in the target table. The Refs belonging the
   * target table will have a corresponding method to model this kind of relations.
   * <p>
   * When modeling associative tables, use target to define the generated DSL (see Example 2).
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
  public TargetReferenceBuilder target(String name)
  {
    if (target != null)
    {
      throw new IllegalArgumentException("Multiple definition of target not allowed");
    }
    target = new TargetReferenceBuilder(this, name);
    return target;
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
    String p_localName = getUsedName(localName, columnBuilder.getColumnJavaName());
    String p_targetName = getUsedName(targetName, columnBuilder.getTableBuilder().getJavaName());

    // Avoid null pointer for Apache Velocity
    String p_localDescription = localDescription != null ? localDescription : "";
    String p_targetDescription = targetDescription != null ? targetDescription : "";

    columnBuilder.addRelation(new Relation(column, p_localName, p_localDescription, p_targetName, p_targetDescription,
        targetMin, targetMax));
  }

  private static String getUsedName(String name, String columnJavaName)
  {
    if (name == null)
    {
      return CamelCase.makeFirstLowerCase(columnJavaName) + "To";
    }
    return name;
  }

  Column getColumn()
  {
    return column;
  }

  LocalReferenceBuilder getLocal()
  {
    return local;
  }

  TargetReferenceBuilder getTarget()
  {
    return target;
  }


  public static class LocalReferenceBuilder
  {
    private final RelationBuilder parent;

    public LocalReferenceBuilder(RelationBuilder parent, String name)
    {
      this.parent = parent;
      parent.localName = name;
    }

    public LocalReferenceBuilder description(String description)
    {
      parent.localDescription = description;
      return this;
    }

    /**
     * Describes the relation regarding the entities in the target table. The Refs belonging the
     * target table will have a corresponding method to model this kind of relations.
     * <p>
     * When modeling associative tables, use target to define the generated DSL (see Example 2).
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
    public TargetReferenceBuilder target(String name)
    {
      return parent.target(name);
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

    public Table build()
    {
      return parent.build();
    }

    public String getName()
    {
      return parent.localName;
    }

    public String getDescription()
    {
      return parent.localDescription;
    }
  }

  public static class TargetReferenceBuilder
  {
    private final RelationBuilder parent;


    public TargetReferenceBuilder(RelationBuilder parent, final String name)
    {
      this.parent = parent;
      parent.targetName = name;
    }

    public TargetReferenceBuilder description(String description)
    {
      parent.targetDescription = description;
      return this;
    }

    public TargetReferenceBuilder min(int min)
    {
      parent.targetMin = min;
      return this;
    }

    public TargetReferenceBuilder max(int max)
    {
      parent.targetMax = max;
      return this;
    }

    /**
     * Describes the relation regarding the entities in the current table. The Refs belonging the
     * current table will have a corresponding method to model this kind of relations.
     * <p>
     * When modeling associative tables, use {@link RelationBuilder#target(String)} to define the
     * DSL features.
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
    public LocalReferenceBuilder local(String name)
    {
      return parent.local(name);
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

    public String getName()
    {
      return parent.targetName;
    }

    public String getDescription()
    {
      return parent.targetDescription;
    }

    public Integer getMin()
    {
      return parent.targetMin;
    }

    public Integer getMax()
    {
      return parent.targetMax;
    }
  }

}
