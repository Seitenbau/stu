package com.seitenbau.stu.dbunit.validator;

import com.seitenbau.stu.dbunit.modifier.Replacer;

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
