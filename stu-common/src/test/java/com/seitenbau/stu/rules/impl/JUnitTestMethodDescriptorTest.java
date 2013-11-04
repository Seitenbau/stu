package com.seitenbau.stu.rules.impl;

import static com.seitenbau.stu.asserts.fest.Assertions.assertThat;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.model.FrameworkMethod;
import org.mockito.Mock;

import com.seitenbau.stu.annotation.TestCase;
import com.seitenbau.stu.annotation.TestDesc;
import com.seitenbau.stu.annotation.TestID;
import com.seitenbau.stu.mockito.MockitoRule;
import com.seitenbau.stu.rules.ITestMethodDescriptor;
import com.seitenbau.stu.rules.impl.JUnitTestMethodDescriptor;

public class JUnitTestMethodDescriptorTest
{
  @Rule
  public MockitoRule mockito = new MockitoRule();

  @Mock
  FrameworkMethod fmethod;
  
  Method methodOnlyInActual = getMethod(ActualTestClass.class, "methodOnlyInActual");
  Method methodOverwritten = getMethod(ActualTestClass.class, "methodOverwritten");
  Method methodOnlyInBase = getMethod(BaseTestClass.class, "methodOnlyInBase");

  @Test
  public void gettersWith_methodOnlyInActual()
  {
    // prepare
    ActualTestClass object = new ActualTestClass();
    Integer index = 0;
    when(fmethod.getMethod()).thenReturn(methodOnlyInActual);
    when(fmethod.getName()).thenReturn("methodOnlyInActual()");

    // execute
    ITestMethodDescriptor sut = new JUnitTestMethodDescriptor(fmethod, object, index);

    // verify
    assertThat(sut.getAnnotation(Ignore.class, false)).isNull();
    assertThat(sut.getClassName()).isEqualTo("com.seitenbau.stu.rules.impl.JUnitTestMethodDescriptorTest$ActualTestClass");
    assertThat(sut.getClassCanoncialName()).isEqualTo("com.seitenbau.stu.rules.impl.JUnitTestMethodDescriptorTest.ActualTestClass");
    assertThat(sut.getIndex()).isSameAs(index);
    assertThat(sut.getMethodName()).isEqualTo("methodOnlyInActual()");
    assertThat(sut.getTarget()).isSameAs(object);
    assertThat(sut.toString()).isEqualTo("com.seitenbau.stu.rules.impl.JUnitTestMethodDescriptorTest.ActualTestClass::methodOnlyInActual()");
  }
  
  @Test
  public void gettersWith_methodOverwritten()
  {
    // prepare
    ActualTestClass object = new ActualTestClass();
    Integer index = 0;
    when(fmethod.getMethod()).thenReturn(methodOverwritten);
    when(fmethod.getName()).thenReturn("methodOverwritten()");
    
    // execute
    ITestMethodDescriptor sut = new JUnitTestMethodDescriptor(fmethod, object, index);
    
    // verify
    assertThat(sut.getAnnotation(Ignore.class, false)).isNull();
    assertThat(sut.getClassName()).isEqualTo("com.seitenbau.stu.rules.impl.JUnitTestMethodDescriptorTest$ActualTestClass");
    assertThat(sut.getClassCanoncialName()).isEqualTo("com.seitenbau.stu.rules.impl.JUnitTestMethodDescriptorTest.ActualTestClass");
    assertThat(sut.getIndex()).isSameAs(index);
    assertThat(sut.getMethodName()).isEqualTo("methodOverwritten()");
    assertThat(sut.getTarget()).isSameAs(object);
    assertThat(sut.toString()).isEqualTo("com.seitenbau.stu.rules.impl.JUnitTestMethodDescriptorTest.ActualTestClass::methodOverwritten()");
  }
  
  @Test
  public void gettersWith_methodOnlyInBase()
  {
    // prepare
    ActualTestClass object = new ActualTestClass();
    Integer index = 0;
    when(fmethod.getMethod()).thenReturn(methodOnlyInBase);
    when(fmethod.getName()).thenReturn("methodOnlyInBase()");
    
    // execute
    ITestMethodDescriptor sut = new JUnitTestMethodDescriptor(fmethod, object, index);
    
    // verify
    assertThat(sut.getAnnotation(Ignore.class, false)).isNull();
    assertThat(sut.getClassName()).isEqualTo("com.seitenbau.stu.rules.impl.JUnitTestMethodDescriptorTest$ActualTestClass");
    assertThat(sut.getClassCanoncialName()).isEqualTo("com.seitenbau.stu.rules.impl.JUnitTestMethodDescriptorTest.ActualTestClass");
    assertThat(sut.getIndex()).isSameAs(index);
    assertThat(sut.getMethodName()).isEqualTo("methodOnlyInBase()");
    assertThat(sut.getTarget()).isSameAs(object);
    assertThat(sut.toString()).isEqualTo("com.seitenbau.stu.rules.impl.JUnitTestMethodDescriptorTest.ActualTestClass::methodOnlyInBase()");
  }
  
  @Test
  public void gettersWith_NoInstance()
  {
    // prepare
    Integer index = 0;
    when(fmethod.getMethod()).thenReturn(methodOnlyInBase);
    when(fmethod.getName()).thenReturn("methodOnlyInBase()");

    // execute
    ITestMethodDescriptor sut = new JUnitTestMethodDescriptor(fmethod, null, index);

    // verify
    assertThat(sut.getAnnotation(Ignore.class, false)).isNull();
    assertThat(sut.getClassName()).isEqualTo("com.seitenbau.stu.rules.impl.JUnitTestMethodDescriptorTest$BaseTestClass");
    assertThat(sut.getClassCanoncialName()).isEqualTo("com.seitenbau.stu.rules.impl.JUnitTestMethodDescriptorTest.BaseTestClass");
    assertThat(sut.getIndex()).isSameAs(index);
    assertThat(sut.getMethodName()).isEqualTo("methodOnlyInBase()");
    assertThat(sut.getTarget()).isNull();
    assertThat(sut.toString()).isEqualTo("com.seitenbau.stu.rules.impl.JUnitTestMethodDescriptorTest.BaseTestClass::methodOnlyInBase()");
  }
  
  @Test
  public void getTestFrameworkMethod()
  {
    // execute
    JUnitTestMethodDescriptor sut = new JUnitTestMethodDescriptor(fmethod, new ActualTestClass(), null);

    // verify
    assertThat( sut.getTestFrameworkMethod() ) .isSameAs( fmethod );
  }
  
  @Test
  public void getAnnotation_DirectlyOnMethod()
  {
    // prepare
    ActualTestClass object = new ActualTestClass();
    Integer index = 0;
    fmethod = new FrameworkMethod(methodOverwritten);
    
    // execute
    JUnitTestMethodDescriptor sut = new JUnitTestMethodDescriptor(fmethod, object, index);

    { // verify annotation on Actual Class Method
      TestCase anno = sut.getAnnotation(TestCase.class, false);
      assertThat(anno).isNotNull();
      assertThat(anno.value()[0]).isEqualTo("case");
      
      // serch hierarchical
      anno = sut.getAnnotation(TestCase.class, true);
      assertThat(anno).isNotNull();
      assertThat(anno.value()[0]).isEqualTo("case");
    }
  }
  
  @Test
  public void getAnnotation_OnBaseMethod()
  {
    // prepare
    ActualTestClass object = new ActualTestClass();
    Integer index = 0;
    fmethod = new FrameworkMethod(methodOverwritten);
    
    // execute
    JUnitTestMethodDescriptor sut = new JUnitTestMethodDescriptor(fmethod, object, index);

    { // verify annotation on Actual Class Method
      TestDesc anno = sut.getAnnotation(TestDesc.class, false);
      assertThat(anno).isNull();
      
      // serch hierarchical
      anno = sut.getAnnotation(TestDesc.class, true);
      assertThat(anno).isNotNull();
      assertThat(anno.value()[0]).isEqualTo("base-desc");
    }
  }
  
  @Test
  public void getAnnotation_OnBaseClass()
  {
    // prepare
    ActualTestClass object = new ActualTestClass();
    Integer index = 0;
    fmethod = new FrameworkMethod(methodOverwritten);
    
    // execute
    JUnitTestMethodDescriptor sut = new JUnitTestMethodDescriptor(fmethod, object, index);

    { // verify annotation on Actual Class Method
      TestID anno = sut.getAnnotation(TestID.class, false);
      assertThat(anno).isNull();
      
      // serch hierarchical
      anno = sut.getAnnotation(TestID.class, true);
      assertThat(anno).isNotNull();
      assertThat(anno.value()).isEqualTo("rainer");
    }
  }
  
  @Test
  public void getAnnotation_OnBaseClassFromUpper()
  {
    // prepare
    UpperTestClass object = new UpperTestClass();
    Integer index = 0;
    fmethod = new FrameworkMethod(methodOverwritten);
    
    // execute
    JUnitTestMethodDescriptor sut = new JUnitTestMethodDescriptor(fmethod, object, index);
    
    { // verify annotation on Actual Class Method
      TestID anno = sut.getAnnotation(TestID.class, false);
      assertThat(anno).isNull();
      
      // serch hierarchical
      anno = sut.getAnnotation(TestID.class, true);
      assertThat(anno).isNotNull();
      assertThat(anno.value()).isEqualTo("rainer");
    }
  }
  
  @Test
  public void getAnnotation_OnBaseClassFromUpperStatic()
  {
    // prepare
    Integer index = 0;
    fmethod = new FrameworkMethod(methodOverwritten);
    
    // execute
    JUnitTestMethodDescriptor sut = new JUnitTestMethodDescriptor(fmethod, null, index);
    
    { // verify annotation on Actual Class Method
      TestID anno = sut.getAnnotation(TestID.class, false);
      assertThat(anno).isNull();
      
      // serch hierarchical
      anno = sut.getAnnotation(TestID.class, true);
      assertThat(anno).isNotNull();
      assertThat(anno.value()).isEqualTo("rainer");
    }
  }
  
  @Test
  public void getAnnotation_None()
  {
    // prepare
    Integer index = 0;
    fmethod = new FrameworkMethod(methodOverwritten);
    
    // execute
    JUnitTestMethodDescriptor sut = new JUnitTestMethodDescriptor(fmethod, null, index);
    
    { // verify annotation on Actual Class Method
      Test anno = sut.getAnnotation(Test.class, false);
      assertThat(anno).isNull();
      
      // serch hierarchical
      anno = sut.getAnnotation(Test.class, true);
      assertThat(anno).isNull();
    }
  }

  public static class Deeper {}
  
  @TestID("rainer")
  @TestCase("base-class-case")
  @TestDesc("base-class-desc")
  public static class BaseTestClass extends Deeper {
    public void methodOnlyInBase() { }
    @TestCase("base-case")
    @TestDesc("base-desc")
    @Ignore 
    public void methodOverwritten() { }
  }

  public static class ActualTestClass extends BaseTestClass {
    public void methodOnlyInActual() { }
    @TestCase("case")
    public void methodOverwritten()  { }
  }
  
  public static class UpperTestClass extends BaseTestClass {
  }
  
  Method getMethod(Class<?> clazz, String name)
  {
    try
    {
      return clazz.getMethod(name);
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  } 
}
