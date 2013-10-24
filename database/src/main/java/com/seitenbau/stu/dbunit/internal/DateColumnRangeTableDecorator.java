package com.seitenbau.stu.dbunit.internal;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.ITable;

public class DateColumnRangeTableDecorator extends DateColumnCompareTableDecorator
{

  private DateComparator comparator;

  private final static String RANGE = "${actual_datetime_range}";

  private String range = RANGE;

  public DateColumnRangeTableDecorator(DateComparator comparator, String rangeColumn, ITable table)
  {
    super(rangeColumn, table);
    this.comparator = comparator;
  }

  public Object createCompareModel(int row, String column) throws DataSetException
  {
    Object value = table.getValue(row, column);
    if (value.equals(range))
    {
      return comparator;
    }
    return super.createCompareModel(row, column);
  }

}
