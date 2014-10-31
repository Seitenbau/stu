package com.seitenbau.stu.testdata;

import static org.junit.Assert.*;

import org.junit.Test;

public class NullParameterDescriptorTest
{

  NullParameterDescriptor descriptor = new NullParameterDescriptor();

  @Test
  public void testGetName()
  {
    assertEquals(NullParameterDescriptor.NAME, descriptor.getName());
  }

  @Test
  public void testGetMetadata() throws Exception
  {
    assertTrue(descriptor.getMetadata(null, null).isEmpty());
  }
  
  @Test
  public void testIsEmptyable() throws Exception
  {
    assertFalse(descriptor.isEmptyable());
  }

}
