package com.seitenbau.stu.testdata;

import java.lang.reflect.Field;

public class IndexFieldSetter
{
  
  private static class NullIndexFieldSetter extends IndexFieldSetter
  {

    public NullIndexFieldSetter()
    {
      super(null);
    }

    @Override
    public void next(Object model)
    {
    }

  }
  
  private final Field indexField;

  private Index index;

  private IndexFieldSetter(Field indexField)
  {
    this.indexField = indexField;
    this.index = new Index(0);
  }

  public void next(Object model)
  {
    this.index = index.next();
    try
    {
      indexField.set(model, index);
    }
    catch (Exception exp)
    {
      throw new SetupIndexFieldFailedException(exp);
    }
  }
  
  public static IndexFieldSetter create(Class<?> model)
  {
    Field[] fields = model.getFields();
    for (Field field : fields)
    {
      if (field.getType().equals(Index.class))
      {
        return new IndexFieldSetter(field);
      }
    }
    return new NullIndexFieldSetter();
  }

}
