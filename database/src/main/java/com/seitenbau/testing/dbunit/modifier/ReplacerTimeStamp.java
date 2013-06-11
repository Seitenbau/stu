package com.seitenbau.testing.dbunit.modifier;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Replaces the String literal inside the DataSet with the current or
 * a given Date.
 */
public class ReplacerTimeStamp extends Replacer
{
  private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

  private String fDatePattern = DEFAULT_DATE_PATTERN;

  private Date fUsedDate;

  /**
   * Initializes the class withe the String literal that should be
   * replaced. The date is set to the current time.
   * 
   * @param markerString The String literal that should be replaced.
   */
  public ReplacerTimeStamp(String markerString)
  {
    this(markerString, new Date());
  }

  /**
   * Initializes the class withe the String literal that should be
   * replaced. The date is set to the given Date.
   * 
   * @param markerString The String literal that should be replaced.
   * 
   * @param date The date should be used as replace date.
   */
  public ReplacerTimeStamp(String markerString, Date date)
  {
    super(markerString, null);
    setReplaceDate(date);
    fUsedDate = date;
  }

  /**
   * Getter
   * 
   * @return fDatePattern, NIE {@code null}.
   */
  public String getDatePattern()
  {
    if (fDatePattern == null)
    {
      return DEFAULT_DATE_PATTERN;
    }
    return fDatePattern;
  }

  /**
   * Getter
   * 
   * @return fUsedDate
   */
  public Date getUsedDate()
  {
    return fUsedDate;
  }

  /**
   * Setter The pattern is applied not until the
   * {@link #setReplaceDate(Date)} is called!
   * 
   * @param datePattern Sets the {@link SimpleDateFormat} that should
   *        be used to transform the Date into a date String. If the
   *        provided pattern is {@code null}, the default pattern
   *        {@link #DEFAULT_DATE_PATTERN} is used.
   */
  public void setDatePattern(String datePattern)
  {
    fDatePattern = datePattern;
  }

  /**
   * Setter
   * 
   * @param date Sets the Date that is used as replacement.
   */
  public void setReplaceDate(Date date)
  {
    fUsedDate = date;
    setReplaceValue(makeDate(date));
  }

  private String makeDate(Date date)
  {
    return new SimpleDateFormat(getDatePattern()).format(date);
  }
}
