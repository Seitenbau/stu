package com.seitenbau.testing.dbunit.validator;

import com.seitenbau.testing.dbunit.modifier.Replacer;

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
