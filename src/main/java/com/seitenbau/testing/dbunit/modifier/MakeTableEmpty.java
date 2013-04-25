package com.seitenbau.testing.dbunit.modifier;

import org.dbunit.dataset.IDataSet;

import com.seitenbau.testing.dbunit.tester.DataSetUtil;

/**
 * Entfernt alle Zeilen aus dem angebenen Tabellen. Convenient Klasse
 * um {@link DataSetUtil#filterOutTableRows(IDataSet, String...)}
 */
@Deprecated // Use RemoveTableConent instead.
public class MakeTableEmpty implements IDataSetFilter
{

  private String[] tableName = null;

  /**
   * Entfernt alle Zeilen aus dem angebenen Tabellen. Convenient
   * Klasse um
   * {@link DataSetUtil#filterOutTableRows(IDataSet, String...)}
   * 
   * @param tablesToMakeEmpty
   *          Liste an Tabellen die geleert werden sollen.
   */
  public MakeTableEmpty(String... tablesToMakeEmpty)
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
