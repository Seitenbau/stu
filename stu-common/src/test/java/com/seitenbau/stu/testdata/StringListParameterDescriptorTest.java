package com.seitenbau.stu.testdata;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.seitenbau.stu.testdata.InvalidValues;
import com.seitenbau.stu.testdata.MetaValue;
import com.seitenbau.stu.testdata.StringListParameterDescriptor;
import com.seitenbau.stu.testdata.Value;

public class StringListParameterDescriptorTest
{

  StringListParameterDescriptor parameterDescriptor;

  static class StringListTestData
  {
    @InvalidValues(values = {
        @Value(value = "c", metadata = {@MetaValue(type = "name", value = "tester")}),
        @Value(value = "d")
    })
    public List<String> names;
    
    public List<String> noInvalidValues;
    
  }

  @Before
  public void setUp() throws Exception
  {
    parameterDescriptor = new StringListParameterDescriptor(
        StringListTestData.class.getField("names"));
  }

  @Test
  public void testPermutations()
  {
    List<List<String>> permutations = parameterDescriptor
        .permutations(new String[] {"A", "B", "C"});
    assertEquals(Arrays.asList(new String[] {"A"}), permutations.get(0));
    assertEquals(Arrays.asList(new String[] {"A", "B"}), permutations.get(1));
    assertEquals(Arrays.asList(new String[] {"A", "B", "C"}),
        permutations.get(2));
  }

  @Test
  public void testGetMetadata_ValueNoList() throws Exception
  {
    List<String> metadata = parameterDescriptor.getMetadata("name", "");
    assertEquals(Collections.EMPTY_LIST, metadata);
  }

  @Test
  public void testGetMetadata_ValueListWithMetadata() throws Exception
  {
    List<String> value = Arrays.asList("c");
    List<String> metadata = parameterDescriptor.getMetadata("name", value);
    assertEquals(1, metadata.size());
  }
  
  @Test
  public void testGetMetadata_ValueListWithMetadataContainsTypeNot() throws Exception
  {
    List<String> value = Arrays.asList("c");
    List<String> metadata = parameterDescriptor.getMetadata("type", value);
    assertEquals(0, metadata.size());
  }
  
  @Test
  public void testGetMetadata_ValueListWithoutMetadata() throws Exception
  {
    List<String> value = Arrays.asList("d");
    List<String> metadata = parameterDescriptor.getMetadata("name", value);
    assertEquals(0, metadata.size());
  }
  
  @Test
  public void testGetMetadata_NoInvalidValues() throws Exception
  {
    parameterDescriptor = new StringListParameterDescriptor(
        StringListTestData.class.getField("noInvalidValues"));
    List<String> value = Arrays.asList("d");
    List<String> metadata = parameterDescriptor.getMetadata("name", value);
    assertEquals(0, metadata.size());
  }

}
