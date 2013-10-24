package com.seitenbau.stu.dbunit.modifier;

/**
 * Base Interface that enables manipulation of DataSet content during runtime.
 */
public interface IDataSetReplacer extends IDataSetModifier
{
  /**
   * Getter for the marker.
   * 
   * @return Returns the String literal of the dataSet that should be
   *         replaced.
   */
  public String getMarkerString();

  /**
   * Getter for the replacement.
   * 
   * @return Returns the Object that replaces the String literal
   *         inside the dataSet
   */
  public Object getReplacementObject();
}
