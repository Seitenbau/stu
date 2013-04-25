package com.seitenbau.testing.dbunit.modifier;

import org.dbunit.dataset.IDataSet;

/**
 * Interface für alle Modifier die eigentlich einen Filter auf das
 * DataSet anwenden.
 */
public interface IDataSetFilter extends IDataSetModifier
{
  /**
   * Führt eine Filterung auf dem übergeben DataSet aus.
   * 
   * @param current
   *          Das aktuelle DataSet.
   * 
   * @return Das gefilterte DataSet
   * 
   * @throws Exception
   *           Fehler.
   */
  public IDataSet filter(IDataSet current) throws Exception;
}
