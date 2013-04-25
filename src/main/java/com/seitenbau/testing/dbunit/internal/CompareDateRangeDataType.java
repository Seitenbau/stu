package com.seitenbau.testing.dbunit.internal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.dbunit.dataset.ITable;
import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.TypeCastException;

public class CompareDateRangeDataType extends DataType
{

  @Override
  public int compare(Object obj1, Object obj2) throws TypeCastException
  {
    if (obj1 instanceof DateCompareType)
    {
      DateCompareType date = (DateCompareType) obj1;
      if (obj2 instanceof DateComparator)
      {
        return isDateEquals(obj2, date);
      }
      else if (obj2 instanceof DateCompareType)
      {
        DateCompareType dateType = (DateCompareType) obj2;
        return isDateEquals(date, dateType);
      }
      else
      {
        throw new IllegalArgumentException();
      }
    }
    else if (obj2 instanceof DateCompareType)
    {
      DateCompareType date = (DateCompareType) obj2;
      if (obj1 instanceof DateComparator)
      {
        return isDateEquals(obj1, date);
      }
      else
      {
        throw new IllegalArgumentException();
      }
    }
    throw new IllegalArgumentException();
  }

  private int isDateEquals(Object obj2, DateCompareType date)
  {
    DateComparator comparator = (DateComparator) obj2;
    Date dateTimeObj = (Date) date.getDate();
    if (comparator.isDateSimilarTo(dateTimeObj))
    {
      return 0;
    }
    return 1;
  }

  private int isDateEquals(DateCompareType date, DateCompareType dateType)
  {
    Date expected = (Date) typeCastDate(date.getDate());
    Date actual = (Date) typeCastDate(dateType.getDate());
    if (expected.equals(actual))
    {
      return 0;
    }
    return 1;
  }

  @Override
  public int getSqlType()
  {
    return 0;
  }

  @Override
  public Object getSqlValue(int column, ResultSet resultSet) throws SQLException, TypeCastException
  {
    return null;
  }

  @Override
  public Class<?> getTypeClass()
  {
    return null;
  }

  @Override
  public boolean isDateTime()
  {
    return false;
  }

  @Override
  public boolean isNumber()
  {
    return false;
  }

  @Override
  public void setSqlValue(Object value, int column, PreparedStatement statement) throws SQLException, TypeCastException
  {
  }

  @Override
  public Object typeCast(Object value) throws TypeCastException
  {
    return null;
  }

  public Object typeCastDate(Object value)
  {
    if (value == null || value == ITable.NO_VALUE)
    {
      return null;
    }

    if (value instanceof java.sql.Timestamp)
    {
      return value;
    }

    if (value instanceof java.util.Date)
    {
      java.util.Date date = (java.util.Date) value;
      return new java.sql.Timestamp(date.getTime());
    }

    if (value instanceof Long)
    {
      Long date = (Long) value;
      return new java.sql.Timestamp(date.longValue());
    }

    if (value instanceof String)
    {
      String stringValue = (String) value;

      // Probably a java.sql.Date, try it just in case!
      if (stringValue.length() == 10)
      {
        try
        {
          long time = java.sql.Date.valueOf(stringValue).getTime();
          return new java.sql.Timestamp(time);
        }
        catch (IllegalArgumentException e)
        {
          // Was not a java.sql.Date, let Timestamp handle this value
        }
      }
      return java.sql.Timestamp.valueOf(stringValue);
    }
    throw new IllegalArgumentException();
  }

}
