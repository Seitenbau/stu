package com.seitenbau.stu.database.validator;

import com.seitenbau.stu.database.modifier.Replacer;

@Deprecated // not usefull with inmemory Datasets
public class ValueSet extends Replacer
{

  public ValueSet(Object newValue)
  {
    super(newValue);
  }

  public ValueSet(String markerString, Object newValue)
  {
    super(markerString, newValue);
  }

}
