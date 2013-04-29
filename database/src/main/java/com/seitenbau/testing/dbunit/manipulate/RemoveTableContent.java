package com.seitenbau.testing.dbunit.manipulate;

import org.dbunit.dataset.IDataSet;

import com.seitenbau.testing.dbunit.modifier.IDataSetFilter;
import com.seitenbau.testing.dbunit.tester.DataSetUtil;

public class RemoveTableContent implements IDataSetFilter
{

  private String[] tableName = null;

  /**
   * Entfernt alle Zeilen aus dem angebenen Tabellen. Convenient
   * Klasse um
   * {@link DataSetUtil#filterOutTableRows(IDataSet, String...)}
   * 
   * @param tablesToMakeEmpty Liste an Tabellen die geleert werden
   *        sollen.
   */
  public RemoveTableContent(String... tablesToMakeEmpty)
  {
    tableName = tablesToMakeEmpty;
  }

  /**
   * {@inheritDoc}
   */
  public IDataSet filter(IDataSet current) throws Exception
  {
    return DataSetUtil.filterOutTableRows(current, tableName);
  }

}
