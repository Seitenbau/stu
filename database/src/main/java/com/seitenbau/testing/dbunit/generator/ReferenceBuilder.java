package com.seitenbau.testing.dbunit.generator;

import com.seitenbau.testing.util.CamelCase;

public class ReferenceBuilder
{
  private final ColumnBuilder columnBuilder;
  
  private final Column column;
  
  private LocalReferenceBuilder local;

  private RemoteReferenceBuilder remote;
  
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
    return columnBuilder.column(name,  dataType);
  }

  public Table build()
  {
    buildReference();
    return columnBuilder.build();
  }
  
  private void buildReference() {
    String localName = CamelCase.makeFirstLowerCase(columnBuilder.getColumnJavaName()) + "To";
        //column.getTable().getJavaName() + "To";
    String remoteName = CamelCase.makeFirstLowerCase(columnBuilder.getTableBuilder().getTable().getJavaName()) + "To";
    Integer remoteMin = null;
    Integer remoteMax = null;

    if (local != null) {
      localName = local.getName();
    }

    if (remote != null) {
      remoteName = remote.getName();
      remoteMin = remote.getMin();
      remoteMax = remote.getMax();
    }
    columnBuilder.addReference(new Reference(column, localName, remoteName, remoteMin, remoteMax));
  }

  public LocalReferenceBuilder local(String name)
  {
    if (local != null) {
      throw new IllegalArgumentException("...");
    }
    local = new LocalReferenceBuilder(this, name);
    return local;
  }

  public RemoteReferenceBuilder remote(String name)
  {
    if (remote!= null) {
      throw new IllegalArgumentException("...");
    }
    remote = new RemoteReferenceBuilder(this, name);
    return remote;
  }
  
  public static class LocalReferenceBuilder {
    private final ReferenceBuilder parent;
    
    private final String name;

    public LocalReferenceBuilder(ReferenceBuilder parent, String name) {
      this.parent = parent;
      this.name = name;
    }
   
    public RemoteReferenceBuilder remote(String name) {
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
      return name;
    }
  }

  public static class RemoteReferenceBuilder {
    private final ReferenceBuilder parent;
    
    private final String name;
    
    private Integer min;
    
    private Integer max;

    public RemoteReferenceBuilder(ReferenceBuilder parent, final String name) {
      this.parent = parent;
      this.name = name;
    }

    public RemoteReferenceBuilder min(int min)
    {
      this.min = min;
      return this;
    }

    public RemoteReferenceBuilder max(int max)
    {
      this.max = max;
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
      return name;
    }
    
    public Integer getMin()
    {
      return min;
    }

    public Integer getMax()
    {
      return max;
    }
  }

}
