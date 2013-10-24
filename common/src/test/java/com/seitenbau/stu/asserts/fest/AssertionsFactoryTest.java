package com.seitenbau.stu.asserts.fest;

import org.junit.Assert;
import org.junit.Test;

import com.seitenbau.stu.asserts.fest.Assertions;
import com.seitenbau.stu.asserts.fest.AssertionsFactory;
import com.seitenbau.stu.asserts.fest.impl.ExtendedStringAssert;


public class AssertionsFactoryTest
{
  @Test
  public void register() 
  {
    // prepare
    Assert.assertEquals(ExtendedStringAssert.class, Assertions.assertThat("").getClass());
    AssertionsFactory neu = new AssertionsFactory(){
      @Override
      public ExtendedStringAssert create(String text)
      {
        return new MyStringAssert(text);
      }
    };
    
    // execute
    AssertionsFactory.registerFactory(neu);
    
    
    // verify
    Assert.assertEquals(MyStringAssert.class, Assertions.assertThat("").getClass());
    
    // reset
    AssertionsFactory._factory=new AssertionsFactory();
  }
  
  class MyStringAssert extends ExtendedStringAssert {

    public MyStringAssert(String text)
    {
      super(text);
    }
    
  }
}
