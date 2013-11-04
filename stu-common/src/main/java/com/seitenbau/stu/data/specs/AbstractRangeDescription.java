package com.seitenbau.stu.data.specs;

import com.seitenbau.stu.data.PropertySpecification;

public abstract class AbstractRangeDescription<T>
    extends AbstractPropertySpecification<T>
    implements PropertySpecification<T>
{
  private boolean _required;

  public boolean isRequired()
  {
    return _required;
  }

  /**
   * The Field/Property is required to be set.
   * 
   * Default is false. If true, eg. {@code null} will be treated as
   * invalid value. But dependes on concrete impelemntation. For
   * String "" would also be invalid.
   */
  public AbstractRangeDescription<T> required(boolean required)
  {
    _required = required;
    return this;
  }

  private Integer _maxLength;

  private Integer _minLength;

  public Integer getMaxLength()
  {
    return _maxLength;
  }

  public Integer getMinLength()
  {
    return _minLength;
  }

  public AbstractRangeDescription<T> length(int min, int max)
  {
    minLength(min);
    maxLength(max);
    return this;
  }

  public AbstractRangeDescription<T> minLength(int length)
  {
    _minLength = length;
    return this;
  }

  public AbstractRangeDescription<T> maxLength(int length)
  {
    _maxLength = length;
    return this;
  }
}
