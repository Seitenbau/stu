package com.seitenbau.stu.testdata;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.seitenbau.stu.testdata.IntegerParameterDescriptor;
import com.seitenbau.stu.testdata.InvalidValues;
import com.seitenbau.stu.testdata.MetaValue;
import com.seitenbau.stu.testdata.Value;

public class IntegerParameterDescriptorTest
{

  IntegerParameterDescriptor descriptor;
  
  public static class IntegerTestData
  {
    
    @InvalidValues(values={
        @Value(value="42"),
        @Value(value="43", metadata={@MetaValue(type="name", value="Foo")})
    })
    public Integer number;
    
    public Integer noInvalidValues;
    
  }
  
  @Before
  public void setup() throws Exception
  {
    descriptor = new IntegerParameterDescriptor(IntegerTestData.class.getField("number"));
  }
  
  @Test
  public void testGetMetatdata_NoMetadata()
  {
    
    List<String> metadata = descriptor.getMetadata("name", 42);
    assertEquals(Collections.EMPTY_LIST, metadata);
  }
  
  @Test
  public void testGetMetatdata()
  {
    List<String> metadata = descriptor.getMetadata("name", 43);
    assertEquals(Arrays.asList("Foo"), metadata);
  }
  
  @Test
  public void testGetMetatdata_NoInvalidValues() throws Exception
  {
    descriptor = new IntegerParameterDescriptor(IntegerTestData.class.getField("noInvalidValues"));
    List<String> metadata = descriptor.getMetadata("name", 42);
    assertEquals(Collections.EMPTY_LIST, metadata);
  }

}
