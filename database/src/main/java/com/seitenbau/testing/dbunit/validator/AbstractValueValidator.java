package com.seitenbau.testing.dbunit.validator;

import com.seitenbau.testing.dbunit.modifier.IDataSetOverwriteCompare;
import com.seitenbau.testing.dbunit.modifier.Replacer;

public abstract class AbstractValueValidator extends Replacer implements IDataSetOverwriteCompare
{

  public AbstractValueValidator(String markerString)
  {
    super(markerString, null);
    // set replace value because it is not allowed in super() call
    setReplaceValue(this);
  }

}
