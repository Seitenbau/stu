package com.seitenbau.stu.data.detail.impl;

import com.seitenbau.stu.data.detail.Representant;
import com.seitenbau.stu.data.detail.Representant.RepresentantType;

public class TestPropertyValue 
{
  private Representant<?> _rep;

  private String _name;

  private Enum<?> _marker;

  public Enum<?> getMarker()
  {
    return _marker;
  }

  public TestPropertyValue(String name, Enum<?> marker, Representant<?> rep)
  {
    _rep = rep;
    _name = name;
    _marker=marker;
  }

  public RepresentantType getType()
  {
    return _rep.getType();
  }

  public String getName()
  {
    return _name;
  }

  public Object getValue()
  {
    return _rep.getValue();
  }

  @Override
  public String toString()
  {
    return "TestPropertyValue [name=" + _name + ", rep=" + _rep + ", marker=" + _marker + "]";
  }

}
