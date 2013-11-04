package com.seitenbau.stu.database.modifier;

import java.util.Date;

import com.seitenbau.stu.database.util.DbCompare;
import com.seitenbau.stu.database.util.DbCompare.DateCompareImpl;

/**
 * Replaces a String literal inside the dataset with the current or a
 * specific date. Furthermore {@link IDataSetOverwriteCompare} is
 * implemented to enable fuzzy comparison (+-10s) of dates.
 * <p/>
 * It is forbidden to use the original DBunit assert! Please make use
 * of the {@link DatabaseTesterAwareAssertion} instead. It is
 * recommended to use {@link DBAssertion} as static import.
 * */
@Deprecated
// Renaming zu ValueDateEquals
public class ReplacerTimeStampUnsharpCompare extends ReplacerTimeStamp implements IDataSetOverwriteCompare
{
  private static final int ONE_SECOND = 1000;

  private DateCompareImpl _compare;

  public ReplacerTimeStampUnsharpCompare(String markerString, Date datum)
  {
    super(markerString, datum);
    _compare = new DbCompare.DateCompareImpl(10 * ONE_SECOND);
  }

  public ReplacerTimeStampUnsharpCompare(String markerString)
  {
    this(markerString, 10 * ONE_SECOND);
    _compare = new DbCompare.DateCompareImpl(10 * ONE_SECOND);
  }

  public ReplacerTimeStampUnsharpCompare(String markerString, int plusMinusMilliseconds)
  {
    this(markerString, plusMinusMilliseconds, plusMinusMilliseconds);
    _compare = new DbCompare.DateCompareImpl();
  }

  public ReplacerTimeStampUnsharpCompare(String markerString, int minusMilliseconds, int plusMilliseconds)
  {
    super(markerString);
    _compare = new DbCompare.DateCompareImpl(minusMilliseconds, plusMilliseconds);
  }

  public ReplacerTimeStampUnsharpCompare(String markerString, Date now, int plusMinusMilliseconds)
  {
    super(markerString, now);
    _compare = new DbCompare.DateCompareImpl(plusMinusMilliseconds);
  }

  public int compareDataSetElementTo(Object objectToCompareTo)
  {
    if (objectToCompareTo instanceof Date)
    {
      Date expect = getUsedDate();
      _compare.compare(expect, (Date) objectToCompareTo);
    }
    // TODO If no Date is available (e.g. null) an exception could be
    // thrown
    return 0;
  }

  @Override
  public Object getReplacementObject()
  {
    return _compare.createCompareTo(getUsedDate());
  }
}
