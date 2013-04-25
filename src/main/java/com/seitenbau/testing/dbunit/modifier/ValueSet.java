package com.seitenbau.testing.dbunit.modifier;

import com.seitenbau.testing.dbunit.modifier.Replacer;

@Deprecated
// Use Version in validator pacackge!
public class ValueSet extends Replacer
{

  public ValueSet(String markerString, Object newValue)
  {
    super(markerString, newValue);
  }

}
