package com.seitenbau.testing.dbunit.modifier;

/**
 * Value-(Basis)-Klasse um Werte in einem DBunit-xml-DataSet zu
 * ersetzen.
 * <p>
 * Da das DataSet über
 * </p>
 */
public class Replacer implements IDataSetReplacer
{
  protected String fMarkerString = null;

  protected Object fReplaceValue = null;

  /**
   * Spezifiziert die im DBUnit-xml zu ersetzende Zeichenfolge und den
   * dafür einzusetzenden Wert.
   * 
   * @param markerString
   *          Der im DBUnit-xml auftauchende String
   * 
   * @param replaceValue
   *          Der Wert durch den der String ersetzt werden soll.
   */
  public Replacer(String markerString, Object replaceValue)
  {
    fMarkerString = markerString;
    fReplaceValue = replaceValue;
  }

  /**
   * getter
   * 
   * @return fMarkerString
   */
  public String getMarkerString()
  {
    return fMarkerString;
  }

  /**
   * getter
   * 
   * @return fReplaceValue
   */
  public Object getReplacementObject()
  {
    return fReplaceValue;
  }

  /**
   * Setter
   * 
   * @param markerString
   *          fMarkerString
   */
  public void setMarkerString(String markerString)
  {
    fMarkerString = markerString;
  }

  /**
   * Setter
   * 
   * @param replaceValue
   *          fReplaceValue
   */
  public void setReplaceValue(Object replaceValue)
  {
    fReplaceValue = replaceValue;
  }
}
