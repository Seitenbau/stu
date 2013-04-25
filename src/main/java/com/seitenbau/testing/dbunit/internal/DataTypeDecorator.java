package com.seitenbau.testing.dbunit.internal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.TypeCastException;

/**
 * Dimple DataType Delegator. Used as base class for convenience.
 * Which delegates all method to a given Delegate Instancen.
 * 
 */
public class DataTypeDecorator extends DataType
{
  protected DataType fDelegate;

  protected DataType getDelegate()
  {
    return fDelegate;
  }

  public DataTypeDecorator(DataType delegateTo)
  {
    fDelegate = delegateTo;
  }

  @Override
  public int compare(Object o1, Object o2) throws TypeCastException
  {
    return fDelegate.compare(o1, o2);
  }

  @Override
  public boolean equals(Object obj)
  {
    if (obj instanceof DataTypeDecorator)
    {
      return fDelegate.equals(((DataTypeDecorator) obj).fDelegate);
    }
    return super.equals(obj);
  }

  @Override
  public int hashCode()
  {
    return fDelegate.hashCode();
  }

  @Override
  public int getSqlType()
  {
    return fDelegate.getSqlType();
  }

  @Override
  public Object getSqlValue(int column, ResultSet resultSet) throws SQLException, TypeCastException
  {
    return fDelegate.getSqlValue(column, resultSet);
  }

  @Override
  public Class<?> getTypeClass()
  {
    return fDelegate.getTypeClass();
  }

  @Override
  public boolean isDateTime()
  {
    return fDelegate.isDateTime();
  }

  @Override
  public boolean isNumber()
  {
    return fDelegate.isNumber();
  }

  @Override
  public void setSqlValue(Object value, int column, PreparedStatement statement) throws SQLException, TypeCastException
  {
    fDelegate.setSqlValue(value, column, statement);
  }

  @Override
  public Object typeCast(Object value) throws TypeCastException
  {
    return fDelegate.typeCast(value);
  }

}
