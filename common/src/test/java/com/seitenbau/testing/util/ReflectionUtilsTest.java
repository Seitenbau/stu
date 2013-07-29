package com.seitenbau.testing.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.util.List;
import static com.seitenbau.testing.asserts.fest.Assertions.*;

import org.junit.Test;

@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotation {}
class Param {}
class Param2 extends Param {}
class Param3 extends Param {}
class BaseClass {
  
}
class Class1 extends BaseClass {
  void method_k1_NoAnnotation() {}
  @MyAnnotation void method_k1_Annotation() {}
  @MyAnnotation void method_with_Annotation(Param p) {}
}
class Class2 extends Class1 {
  void method_k2_NoAnnotation() {}
  @MyAnnotation void method_k2_Annotation() {}
  @MyAnnotation void method_with_Annotation(Param p) {}
}
class Class3 extends Class1 {
  void method_k2_NoAnnotation() {}
  @MyAnnotation void method_k2_Annotation() {}
  @MyAnnotation void method_with_Annotation(Param p) {}
  @MyAnnotation void method_with_Annotation(Param3 p) {}
}

public class ReflectionUtilsTest
{
  
  @Test
  public void notUsedAnnoation() 
  {
    List<Method> result = ReflectionUtils.findMethodByAnnotation(Class1.class, Retention.class, true);
    // verify
    assertThat(result).isEmpty();
  }

  @Test
  public void usedAnnotationButBaseClass()
  {
    List<Method> result = ReflectionUtils.findMethodByAnnotation(BaseClass.class, MyAnnotation.class, true);
    // verify
    assertThat(result).isEmpty();
  }

  @Test
  public void usedAnnotationButKlasse1()
  {
    List<Method> result = ReflectionUtils.findMethodByAnnotation(Class1.class, MyAnnotation.class, true);
    // verify
    assertThat(result).hasSize(2);
    assertThat(result).onProperty("name").contains("method_k1_Annotation", "method_with_Annotation");
  }

  @Test
  public void usedAnnotationButKlasse2()
  {
    List<Method> result = ReflectionUtils.findMethodByAnnotation(Class2.class, MyAnnotation.class, true);
    // verify
    assertThat(result).hasSize(3);
    assertThat(result).onProperty("name")
        .containsSequence("method_with_Annotation", "method_k2_Annotation", "method_k1_Annotation");
  }

  @Test
  public void findMethodsWithDifferentParamTypeInChildThanInParent()
  {
    List<Method> result = ReflectionUtils.findMethodByAnnotation(Class3.class, MyAnnotation.class, true);
    // verify
    assertThat(result).hasSize(4);
    assertThat(result).onProperty("name")
        .containsSequence("method_with_Annotation", "method_with_Annotation", "method_k2_Annotation",
            "method_k1_Annotation");
  }
  
  @Test
  public void subclass_none() {
    assertThat(ReflectionUtils.canCast(Object.class, Object.class)).isTrue();
    assertThat(ReflectionUtils.canCast(RuntimeException.class, Object.class)).isTrue();
    assertThat(ReflectionUtils.canCast(Object.class,RuntimeException.class)).isFalse();
  }
}
