package com.seitenbau.testing.dbunit.validator;

import com.seitenbau.testing.dbunit.modifier.IDataSetOverwriteCompare;
import com.seitenbau.testing.dbunit.modifier.Replacer;

public abstract class AbstractValueValidator extends Replacer implements
    IDataSetOverwriteCompare
{

  public AbstractValueValidator(String markerString)
  {
    super(markerString, null);
    // Da this nicht im super() Aufruf erlaubt ist.
    setReplaceValue(this);
  }

}
