package com.seitenbau.stu.database.modifier;

/**
 * Value-(Base)-Class to replace values inside a DBUnit XML DataSet.
 */
public class Replacer implements IDataSetReplacer
{
  protected String fMarkerString = null;

  protected Object fReplaceValue = null;

  /**
   * Specifies the marker String inside the XML DBUnit file and the replacement String that should be inserted.
   * 
   * @param markerString The String inside the DBUnit xml file that should be replaced.
   * 
   * @param replaceValue The replacement String that replaces the marker String.
   */
  @Deprecated
  public Replacer(String markerString, Object replaceValue)
  {
    fMarkerString = markerString;
    fReplaceValue = replaceValue;
  }
  
  /**
   * Specifies the marker String inside the XML DBUnit file and the
   * replacement String that should be inserted.
   * 
   * @param replaceValue The replacement String that replaces the
   *        marker String.
   */
  public Replacer(Object replaceValue)
  {
    this("fake", replaceValue);
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
   * @param markerString fMarkerString
   */
  public void setMarkerString(String markerString)
  {
    fMarkerString = markerString;
  }

  /**
   * Setter
   * 
   * @param replaceValue fReplaceValue
   */
  public void setReplaceValue(Object replaceValue)
  {
    fReplaceValue = replaceValue;
  }
}
