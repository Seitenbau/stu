package com.seitenbau.testing.testdata;

import java.io.Serializable;

public final class Index implements Serializable
{

  private static final long serialVersionUID = 1L;

  private final Integer value;

  public Index(Integer index)
  {
    this.value = index;
  }

  public Index next()
  {
    return new Index(getValue() + 1);
  }

  @Override
  public String toString()
  {
    return "" + getValue();
  }

  public Integer getValue()
  {
    return value;
  }

}
