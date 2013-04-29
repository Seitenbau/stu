package com.seitenbau.testing.testdata;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ValidPartitionTest
{
  
  ValidPartition validPartition;
  
  @Mock
  ParameterDescriptor mockParameterDescriptor;
  
  @Before
  public void setup() throws Exception 
  {
    MockitoAnnotations.initMocks(this);
    validPartition = new ValidPartition(mockParameterDescriptor);
  }
  
  @Test(expected=NoValuesForParameterException.class)
  public void testValidPartition()
  {
    when(mockParameterDescriptor.isOptional()).thenReturn(false);
    when(mockParameterDescriptor.isPrimitiv()).thenReturn(false);
    List<PartitionValue> testValues = new ArrayList<PartitionValue>();
    validPartition.generatePartitionValues(testValues);
  }
  
  @Test
  public void testValidPartition_OptionalTrueAndPrimitiveFalse()
  {
    when(mockParameterDescriptor.isOptional()).thenReturn(true);
    when(mockParameterDescriptor.isPrimitiv()).thenReturn(false);
    when(mockParameterDescriptor.getType()).thenReturn((Class) String.class);
    
    List<PartitionValue> testValues = new ArrayList<PartitionValue>();
    validPartition.generatePartitionValues(testValues);
    assertEquals(2, testValues.size());
    assertEquals(null,  testValues.get(0).getValue());
    assertEquals("",    testValues.get(1).getValue());
  }
  
  @Test
  public void testValidPartition_OptionalTrueAndPrimitiveFalse_TypeString()
  {
    when(mockParameterDescriptor.isOptional()).thenReturn(true);
    when(mockParameterDescriptor.isPrimitiv()).thenReturn(false);
    List<PartitionValue> testValues = new ArrayList<PartitionValue>();
    validPartition.generatePartitionValues(testValues);
    assertEquals(1, testValues.size());
    assertEquals(null, testValues.get(0).getValue());
  }
  
  @Test(expected=NoValuesForParameterException.class)
  public void testValidPartition_OptionalFalseAndPrimitiveTrue()
  {
    when(mockParameterDescriptor.isOptional()).thenReturn(false);
    when(mockParameterDescriptor.isPrimitiv()).thenReturn(true);
    List<PartitionValue> testValues = new ArrayList<PartitionValue>();
    validPartition.generatePartitionValues(testValues);
  }
  
  @Test(expected=NoValuesForParameterException.class)
  public void testValidPartition_OptionalTrueAndPrimitiveTrue()
  {
    when(mockParameterDescriptor.isOptional()).thenReturn(true);
    when(mockParameterDescriptor.isPrimitiv()).thenReturn(true);
    List<PartitionValue> testValues = new ArrayList<PartitionValue>();
    validPartition.generatePartitionValues(testValues);
  }

}
