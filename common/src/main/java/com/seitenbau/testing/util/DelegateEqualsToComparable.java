package com.seitenbau.testing.util;

public class DelegateEqualsToComparable<T> {

  private final Comparable<T> _objectWithEquals;

  public DelegateEqualsToComparable(Comparable<T> itemWithEquals)
  {
    _objectWithEquals = itemWithEquals;
  }

  @Override
  public int hashCode()
  {
    return 1;
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean equals(Object obj)
  {
    if (_objectWithEquals == null)
    {
      if (obj == null)
      {
        return true;
      } else {
        return false;
      }
    }
    return _objectWithEquals.compareTo((T) obj) == 0;
  }
}
