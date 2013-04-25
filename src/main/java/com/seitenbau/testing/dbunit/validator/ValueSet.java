package com.seitenbau.testing.dbunit.validator;

import com.seitenbau.testing.dbunit.modifier.Replacer;


public class ValueSet extends Replacer
{

  public ValueSet(Object newValue)
  {
    super("anything", newValue);
  }
  
  public ValueSet(String markerString, Object newValue)
  {
    super(markerString, newValue);
  }

}
