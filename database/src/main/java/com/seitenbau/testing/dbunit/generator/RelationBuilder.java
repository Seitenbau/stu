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

  
  public Column getColumn()
  {
    return column;
  }

  public LocalReferenceBuilder getLocal()
  {
    return local;
  }

  public TargetReferenceBuilder getTarget()
  {
    return target;
  }

  public RelationBuilder(ColumnBuilder columnBuilder, Column reference)
  {
    this.columnBuilder = columnBuilder;
    this.column = reference;
  }

  public ColumnBuilder column(String name, DataType dataType)
  {
    buildReference();
    return columnBuilder.column(name, dataType);
  }

  public Table build()
  {
    buildReference();
    return columnBuilder.build();
  }
  
  private static String getUsedName(String name, String columnJavaName) 
  {
    if (name == null) 
    {
      return CamelCase.makeFirstLowerCase(columnJavaName) + "To";
    }
    return name;
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
  
  public RelationBuilder description(String description)
  {
    targetDescription = description;
    return this;
  }

  public LocalReferenceBuilder local(String name)
  {
    if (local != null)
    {
      throw new IllegalArgumentException("...");
    }
    local = new LocalReferenceBuilder(this, name);
    return local;
  }

  public TargetReferenceBuilder target(String name)
  {
    if (target != null)
    {
      throw new IllegalArgumentException("...");
    }
    target = new TargetReferenceBuilder(this, name);
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

    public TargetReferenceBuilder target(String name)
    {
      return parent.target(name);
    }

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

    public LocalReferenceBuilder local(String name)
    {
      return parent.local(name);
    }

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
