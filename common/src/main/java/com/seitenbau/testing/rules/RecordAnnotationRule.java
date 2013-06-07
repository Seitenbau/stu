package com.seitenbau.testing.rules;

import java.lang.annotation.Annotation;

import org.junit.runners.model.FrameworkMethod;

/**
 * Base rule to extract an annotation from a test method.
 * 
 * To process the annotation overwrite
 * {@link #processAnnotation(Object)}
 */
public class RecordAnnotationRule<T extends Annotation> extends BeforeAfterRule
{
  private Class<T> _annotation;

  private T _value;

  private boolean _tryClass;

  public RecordAnnotationRule(Class<T> toRecord)
  {
    _annotation = toRecord;
    _tryClass = true;
  }

  /**
   * @param toRecord The Annotation Class
   * @param tryClass Set to true ( default) if you want to fallback to
   *        an annotation on the test Class
   */
  public RecordAnnotationRule(Class<T> toRecord, boolean tryClass)
  {
    _annotation = toRecord;
    _tryClass = tryClass;
  }

  @Override
  protected void before(ITestMethodDescriptor descriptor) throws Throwable {
    _value = descriptor.getAnnotation( _annotation, _tryClass);
    super.before(descriptor);
    processAnnotation(_value);
    // _value has to be set after this point!
    if (isActive())
    {
      beforeWithAnnotation(_value);
    }
  }
  
  @Override
  protected void afterSuccess(ITestMethodDescriptor descriptor)
            throws Throwable 
  {
    if (isActive())
    {
      afterSuccessWithAnnotation(descriptor,  _value);
    }
    else
    {
      super.afterSuccess(descriptor);
    }
  }

  @Override
  protected boolean afterError(ITestMethodDescriptor descriptor, Throwable occuredError)
            throws Throwable 
  {
    if (isActive())
    {
      return afterSuccessWithAnnotation(descriptor, occuredError, _value);
    }
    else
    {
      return super.afterError(descriptor, occuredError);
    }
  }
 
  @Override
  protected void afterAll(ITestMethodDescriptor descriptor) throws Throwable 
  {
    if (isActive())
    {
      afterAllWithAnnotation(descriptor, _value);
    }
    super.afterAll(descriptor);
  }

  protected boolean afterSuccessWithAnnotation(ITestMethodDescriptor descriptor,
      Throwable occuredError, T value)
  {
    return true;
  }

  protected void afterSuccessWithAnnotation(ITestMethodDescriptor descriptor, T value)
  {
      
  }
  
  protected void afterAllWithAnnotation(ITestMethodDescriptor descriptor, T value)
  {
  }
  
  protected void beforeWithAnnotation(T value)
  {

  }

  protected void afterWithAnnotation(T value)
  {

  }

  /**
   * Called from the {@link #before(FrameworkMethod)} after reading
   * the annotation.
   * 
   * Overwrite this method to process your annotation.
   * 
   * @param annotation The Annotation, or {@code null} if no
   *        annotation type had been found.
   */
  protected void processAnnotation(T annotation)
  {
  }

  /**
   * Called from the {@link #before(FrameworkMethod)} after reading
   * the annotation. Called only when there was no annotation on the
   * method.
   * 
   * Overwrite this method to process your annotation.
   * 
   * @param annotation The Annotation on the test class, or
   *        {@code null} if no annotation type had been found.
   */
  protected void processClassAnnotation(T value)
  {
  }

  /**
   * get the raw annotation
   * 
   * @return Returns the extracted annotation, of {@code null} if
   *         there was no annotation.
   */
  public T getAnnotation()
  {
    return _value;
  }

  /**
   * Detects if the annotation was present on the testmethod.
   * 
   * @return {@code true} if the annotation was present otherwiese
   *         {@code false}
   */
  public boolean isActive()
  {
    return _value != null;
  }

  public static <TYPE extends Annotation> RecordAnnotationRule<TYPE> of(Class<TYPE> clazz)
  {
    return new RecordAnnotationRule<TYPE>(clazz);
  }
}
