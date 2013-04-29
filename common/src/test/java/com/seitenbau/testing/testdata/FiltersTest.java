package com.seitenbau.testing.testdata;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.seitenbau.testing.testdata.Filters.EmptyValueFilter;

public class FiltersTest
{

  @Mock
  PartitionValue partitionValue;
  
  @Before
  public void setup()
  {
    MockitoAnnotations.initMocks(this);
  }
  
  @Test
  public void createFilterInstance()
  {
    new Filters();
  }
  
  @Test
  public void testIsGeneratedTextValue_Empty() throws Exception
  {
    assertFalse(Filters.isGeneratedTextValue(""));
  }
  
  @Test
  public void testIsGeneratedTextValue_Null() throws Exception
  {
    assertFalse(Filters.isGeneratedTextValue(null));
  }
  
  @Test
  public void testIsGeneratedTextValue_somexx() throws Exception
  {
    assertTrue(Filters.isGeneratedTextValue("somexx"));
  }
  
  @Test
  public void testEmptyValueFilter_ValueNull() throws Exception
  {
    EmptyValueFilter filter = new EmptyValueFilter();
    assertFalse(filter.filter(partitionValue));
  }
  
  @Test
  public void testEmptyValueFilter_ValueInteger() throws Exception
  {
    when(partitionValue.getValue()).thenReturn(Integer.valueOf(42));
    EmptyValueFilter filter = new EmptyValueFilter();
    assertFalse(filter.filter(partitionValue));
  }
  
  @Test
  public void testEmptyValueFilter_ValueNonEmptyString() throws Exception
  {
    when(partitionValue.getValue()).thenReturn("42");
    EmptyValueFilter filter = new EmptyValueFilter();
    assertFalse(filter.filter(partitionValue));
  }

  @Test
  public void testEmptyValueFilter_ValueEmptyString() throws Exception
  {
    when(partitionValue.getValue()).thenReturn("");
    EmptyValueFilter filter = new EmptyValueFilter();
    assertTrue(filter.filter(partitionValue));
  }
  
  @Test
  public void testEmptyValueFilter_ValueEmptyList() throws Exception
  {
    when(partitionValue.getValue()).thenReturn(Collections.EMPTY_LIST);
    EmptyValueFilter filter = new EmptyValueFilter();
    assertTrue(filter.filter(partitionValue));
  }
  
  @Test
  public void testEmptyValueFilter_ValueNonEmptyList() throws Exception
  {
    when(partitionValue.getValue()).thenReturn(Arrays.asList("1"));
    EmptyValueFilter filter = new EmptyValueFilter();
    assertFalse(filter.filter(partitionValue));
  }
  
}
