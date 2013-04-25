package com.seitenbau.testing.dbunit.modifier;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Ersetzt ein StringLiteral im DataSet durch das aktuelle oder ein
 * spezifisches Datum.
 */
public class ReplacerTimeStamp extends Replacer
{
  private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

  private String fDatePattern = DEFAULT_DATE_PATTERN;

  private Date fUsedDate;

  /**
   * Initialisiert die Klasse mit dem zu ersetzenden StringLiteral.
   * Das Datum wir auf die Aktuelle Zeit gesetzt.
   * 
   * @param markerString Das zu ersetzende StringLiteral
   */
  public ReplacerTimeStamp(String markerString)
  {
    this(markerString, new Date());
  }

  /**
   * Initialisiert die Klasse mit dem zu ersetzenden StringLiteral und
   * manuell gesetztem Datum.
   * 
   * @param markerString Das zu ersetzende StringLiteral
   * 
   * @param datum Das Datum welches eingesetzt wird.
   */
  public ReplacerTimeStamp(String markerString, Date datum)
  {
    super(markerString, null);
    setReplaceDate(datum);
    fUsedDate = datum;
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
   * Setter
   * <p>
   * Das Muster wird erst bei einem Aufruf von
   * {@link #setReplaceDate(Date)} angewandt!
   * </p>
   * 
   * @param datePattern Setzt das {@link SimpleDateFormat} Muster das
   *        genutzt wird um das Datum in eine Zeichenfolge zu wandeln.
   *        Wenn {@code null} übergeben wird, wird das Muster
   *        zurückgesetzt auf den default Wert
   *        {@link #DEFAULT_DATE_PATTERN}.
   */
  public void setDatePattern(String datePattern)
  {
    fDatePattern = datePattern;
  }

  /**
   * Setter
   * 
   * @param datum Setzt das Datum welches zum Ersetzen genutzt wird.
   */
  public void setReplaceDate(Date datum)
  {
    fUsedDate = datum;
    setReplaceValue(makeDatum(datum));
  }

  private String makeDatum(Date datum)
  {
    return new SimpleDateFormat(getDatePattern()).format(datum);
  }
}
