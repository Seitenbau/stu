package com.seitenbau.testing.dbunit.generator;

import java.util.HashSet;
import java.util.Set;

public class ColumnMetaDataBuilder
{

  private Set<String> flags;

  public ColumnMetaDataBuilder()
  {
    flags = new HashSet<String>();
  }

  public ColumnMetaData build()
  {
    return new ColumnMetaData(flags);
  }

  public ColumnMetaDataBuilder identifier()
  {
    return setFlag(ColumnMetaData.IDENTIFIER);
  }

  public ColumnMetaDataBuilder autoIncrement()
  {
    return setFlag(ColumnMetaData.AUTO_INCREMENT);
  }

  public ColumnMetaDataBuilder addNextValueMethod()
  {
    return setFlag(ColumnMetaData.ADD_NEXT_METHOD);
  }

  public ColumnMetaDataBuilder autoInvokeNext()
  {
    setFlag(ColumnMetaData.AUTO_INVOKE_NEXT);
    return addNextValueMethod();
  }
  
  public ColumnMetaDataBuilder setFlag(String flag)
  {
    flags.add(flag);
    return this;
  }

}
