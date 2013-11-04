package com.seitenbau.stu.database.validator;

import com.seitenbau.stu.database.modifier.IDataSetOverwriteCompare;
import com.seitenbau.stu.database.modifier.Replacer;

public abstract class AbstractValueValidator extends Replacer implements IDataSetOverwriteCompare
{

  public AbstractValueValidator(String markerString)
  {
    super(markerString, null);
    // set replace value because it is not allowed in super() call
    setReplaceValue(this);
  }

}
