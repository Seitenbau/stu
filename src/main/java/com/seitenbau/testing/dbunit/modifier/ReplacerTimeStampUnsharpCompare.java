package com.seitenbau.testing.dbunit.modifier;

import java.util.Date;

import com.seitenbau.testing.dbunit.util.DbCompare;
import com.seitenbau.testing.dbunit.util.DbCompare.DateCompareImpl;

/**
 * Ersetzt ein StringLiteral im DataSet durch das aktuelle oder ein
 * spezifisches Datum. Zusätzlich wird
 * {@link IDataSetOverwriteCompare} implementiert um bei Asserts den
 * Vergleich unscharf auszuführen (+-10s).
 * <p/>
 * Es darf NICHT der orginal DBUnit Assert eingesetzt werden! Sondern
 * die (fast) 1:1 Kopie: {@link DatabaseTesterAwareAssertion} Version.
 * Am Besten per static Import {@link DBAssertion} nutzen.
 */
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
      _compare.compareValues(expect, (Date) objectToCompareTo);
    }
    // TODO wenn kein Date ? also bsp null -> Fehler Werfen
    return 0;
  }

  @Override
  public Object getReplacementObject()
  {
    return _compare.createCompareTo(getUsedDate());
  }
}
