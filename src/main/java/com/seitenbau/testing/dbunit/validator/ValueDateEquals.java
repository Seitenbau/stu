package com.seitenbau.testing.dbunit.validator;

import java.util.Date;

import com.seitenbau.testing.dbunit.modifier.ReplacerTimeStampUnsharpCompare;

/**
 * Convenient Class for comparing a Database Timestamp. With a delta.
 */
@SuppressWarnings("deprecation")
public class ValueDateEquals extends ReplacerTimeStampUnsharpCompare
{

  /**
   * Compares now againste a Database Timestamp with a delta of
   * +-10Seconds
   * @param markerString
   */
  public ValueDateEquals(String markerString)
  {
    super(markerString);
  }

  /**
   * Compares the given Date againste a Database Timestamp with a
   * delta of +-10Seconds
   */
  public ValueDateEquals(String markerString, Date datum)
  {
    super(markerString, datum);
  }
  
  /**
   * Shifts the 'now' for a specific amount of ms
   * @param msFromNow
   * @return
   */
  public ValueDateEquals shiftDate(int msFromNow) {
    Date datum=new Date(new Date().getTime() + msFromNow);
    setReplaceDate(datum);
    return this;
  }

  /**
   * Compares now againste a Database Timestamp with a delta of
   * +-{plusMinusMilliseconds} Miliseconds
   */
  public ValueDateEquals(String markerString, int plusMinusMilliseconds)
  {
    super(markerString, plusMinusMilliseconds);
  }

  /**
   * Compares now againste a Database Timestamp with a delta of
   * +{plusMilliseconds} Miliseconds and -{minusMilliseconds} ms.
   */
  public ValueDateEquals(String markerString, int minusMilliseconds,
      int plusMilliseconds)
  {
    super(markerString, minusMilliseconds, plusMilliseconds);
  }

  /**
   * Compares the given Date against a Database Timestamp with a delta
   * of +-{plusMinusMilliseconds} Miliseconds
   */
  public ValueDateEquals(String markerString, Date now,
      int plusMinusMilliseconds)
  {
    super(markerString, now, plusMinusMilliseconds);
  }
}
