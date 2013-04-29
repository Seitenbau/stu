package com.seitenbau.testing.dbunit.modifier;

/**
 * Basis Interface zum Austaschen von Inhalten im DataSet zur Laufzeit
 */
public interface IDataSetReplacer extends IDataSetModifier
{
  /**
   * Getter
   * 
   * @return Liefert das String Literal welches im DataSet ersetzt
   *         werden soll.
   */
  public String getMarkerString();

  /**
   * Getter
   * 
   * @return Liefert das Objekt welches das String Lieteral im DataSet
   *         austauscht.
   */
  public Object getReplacementObject();
}
