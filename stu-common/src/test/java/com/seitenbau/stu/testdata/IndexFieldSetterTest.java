package com.seitenbau.stu.testdata;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.seitenbau.stu.testdata.Index;
import com.seitenbau.stu.testdata.IndexFieldSetter;
import com.seitenbau.stu.testdata.SetupIndexFieldFailedException;

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
