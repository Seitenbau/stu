package com.seitenbau.testing.dbunit.generator;

import com.seitenbau.testing.util.CamelCase;

public class ReferenceBuilder
{
  private final ColumnBuilder columnBuilder;

  private final Column column;

  private LocalReferenceBuilder local;

  private RemoteReferenceBuilder remote;
  
  private String localName;

  private String localDescription;

  private String remoteName;

  private String remoteDescription;

  private Integer remoteMin;

  private Integer remoteMax;

  
  public Column getColumn()
  {
    return column;
  }

  public LocalReferenceBuilder getLocal()
  {
    return local;
  }

  public RemoteReferenceBuilder getRemote()
  {
    return remote;
  }

  public ReferenceBuilder(ColumnBuilder columnBuilder, Column reference)
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
    String p_remoteName = getUsedName(remoteName, columnBuilder.getTableBuilder().getJavaName());
    
    // Avoid null pointer for Apache Velocity
    String p_localDescription = localDescription != null ? localDescription : "";
    String p_remoteDescription = remoteDescription != null ? remoteDescription : "";

    columnBuilder.addReference(new Reference(column, p_localName, p_localDescription, p_remoteName, p_remoteDescription,
        remoteMin, remoteMax));

  }
  
  public ReferenceBuilder description(String description)
  {
    remoteDescription = description;
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

  public RemoteReferenceBuilder remote(String name)
  {
    if (remote != null)
    {
      throw new IllegalArgumentException("...");
    }
    remote = new RemoteReferenceBuilder(this, name);
    return remote;
  }

  public static class LocalReferenceBuilder
  {
    private final ReferenceBuilder parent;

    public LocalReferenceBuilder(ReferenceBuilder parent, String name)
    {
      this.parent = parent;
      parent.localName = name;
    }

    public LocalReferenceBuilder description(String description)
    {
      parent.localDescription = description;
      return this;
    }

    public RemoteReferenceBuilder remote(String name)
    {
      return parent.remote(name);
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

  public static class RemoteReferenceBuilder
  {
    private final ReferenceBuilder parent;


    public RemoteReferenceBuilder(ReferenceBuilder parent, final String name)
    {
      this.parent = parent;
      parent.remoteName = name;
    }

    public RemoteReferenceBuilder description(String description)
    {
      parent.remoteDescription = description;
      return this;
    }

    public RemoteReferenceBuilder min(int min)
    {
      parent.remoteMin = min;
      return this;
    }

    public RemoteReferenceBuilder max(int max)
    {
      parent.remoteMax = max;
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
      return parent.remoteName;
    }

    public String getDescription()
    {
      return parent.remoteDescription;
    }

    public Integer getMin()
    {
      return parent.remoteMin;
    }

    public Integer getMax()
    {
      return parent.remoteMax;
    }
  }

}
