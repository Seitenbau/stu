package com.seitenbau.testing.dbunit.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AssociativeTable extends Table
{

  private final List<Column> _associativeColumns;
  private final List<Column> _dataColumns;

  public AssociativeTable(String name, String javaName, String description, long seed,
      Integer infinite, int minEntities, List<ColumnBuilder> columnBuilders)
  {
    super(name, javaName, description, seed, infinite, minEntities, columnBuilders);

    _associativeColumns = new ArrayList<Column>();
    _dataColumns = new ArrayList<Column>();
    for (Column col : super.getColumns())
    {
      if (col.getRelation() != null)
      {
        _associativeColumns.add(col);
      } else {
        _dataColumns.add(col);
      }
    }

    if (_associativeColumns.size() != 2)
    {
      throw new RuntimeException("Illegal associative table (excatly 2 relation columns expected)");
    }
  }

  @Override
  public boolean isAssociativeTable()
  {
    return true;
  }

  public List<Column> getAssociativeColumns()
  {
    return Collections.unmodifiableList(_associativeColumns);
  }

  public List<Column> getDataColumns()
  {
    return Collections.unmodifiableList(_dataColumns);
  }

}
