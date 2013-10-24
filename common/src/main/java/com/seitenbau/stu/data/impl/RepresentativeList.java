package com.seitenbau.stu.data.impl;

import java.util.ArrayList;
import java.util.List;

import com.seitenbau.stu.data.detail.Representant;
import com.seitenbau.stu.data.detail.Representant.RepresentantType;
import com.seitenbau.stu.data.detail.impl.RepresentantImpl;

public class RepresentativeList<T>
{
  List<Representant<T>> _result = new ArrayList<Representant<T>>();

  RepresentantType _addOnlyFilter = null;

  public RepresentativeList(RepresentantType addOnlyFilter)
  {
    _addOnlyFilter = addOnlyFilter;
  }

  public void valid(T value)
  {
    add(RepresentantType.VALID, value);
  }

  public void inValid(T value)
  {
    add(RepresentantType.INVALID, value);
  }

  public List<Representant<T>> getList()
  {
    if (_result == null)
    {
      return new ArrayList<Representant<T>>();
    }
    return _result;
  }

  public void add(RepresentantType type, T value)
  {
    if (type != null && _addOnlyFilter != null)
    {
      if (type.equals(_addOnlyFilter))
      {
        if (!_result.contains(sameValue(value)))
        {
          _result.add(new RepresentantImpl<T>(type, value));
        }
      }
    }
    else
    {
      if (!_result.contains(sameValue(value)))
      {
        _result.add(new RepresentantImpl<T>(type, value));
      }
    }
  }

  private Object sameValue(final T value)
  {
    return new Object()
    {
      public boolean equals(Object obj)
      {
        Representant<?> rep = (Representant<?>) obj;
        if (rep.getValue() == null)
        {
          return value == null;
        }
        return rep.getValue().equals(value);
      }
    };
  }

  public void add(boolean isValid, T... value)
  {
    if (isValid)
    {
      valid(value);
    }
    else
    {
      inValid(value);
    }
  }

  public void valid(T... items)
  {
    if (items == null)
    {
      add(RepresentantType.VALID, null);
    }
    else
    {
      for (T item : items)
      {
        add(RepresentantType.VALID, item);
      }
    }
  }

  public void inValid(T... items)
  {
    if (items == null)
    {
      add(RepresentantType.INVALID, null);
    }
    else
    {
      for (T item : items)
      {
        add(RepresentantType.INVALID, item);
      }
    }
  }
}