package com.seitenbau.testing.testdata;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class IndexFieldSetterTest
{

  @Rule
  public ExpectedException thrown= ExpectedException.none();
  
  public static class IndexedDataSet
  {
    public Index index;
  }
  
  @Test
  public void testNext()
  {
    IndexFieldSetter fieldSetter = IndexFieldSetter.create(IndexedDataSet.class);
    thrown.expect(SetupIndexFieldFailedException.class);
    fieldSetter.next(new Object());
  }

}
