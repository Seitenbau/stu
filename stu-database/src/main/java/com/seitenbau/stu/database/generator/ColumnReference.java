package com.seitenbau.stu.database.generator;

import java.util.ArrayList;
import java.util.List;


public class ColumnReference
{
  public Column _foreignColumn;

  private final FutureColumn _foreignFutureColumn;
  
  private final List<Column> _referencedByColumns;

  private final String _localName;

  private final String _localDescription;

  private final String _localMultiplicty;

  private final String _foreignName;

  private final String _foreignDescription;

  private final String _foreignMultiplicty;

  ColumnReference(FutureColumn foreignColumn, String localName, String localDescription, String localMultiplicty, String foreignName,
      String foreignDescription, String foreignMultiplicity)
  {
    _foreignFutureColumn = foreignColumn;
    _localName = localName;
    _localDescription = localDescription;
    _localMultiplicty = localMultiplicty;
    _foreignName = foreignName;
    _foreignDescription = foreignDescription;
    _foreignMultiplicty = foreignMultiplicity;
    
    _referencedByColumns = new ArrayList<Column>();
  }

  public Column getForeignColumn()
  {
    if (_foreignColumn == null) {
      
      _foreignColumn = _foreignFutureColumn.getFuture();
      for (Column column : _referencedByColumns)
      {
        _foreignColumn._referencedBy.add(column);
      }
    }
    
    return _foreignColumn;
  }
  

  public void addReferencedBy(Column column)
  {
    if (_foreignColumn != null || _foreignFutureColumn.isAvailable()) {
      getForeignColumn()._referencedBy.add(column);
    }
    else {
      _referencedByColumns.add(column);
    }
  }

  public String getLocalName()
  {
    return _localName;
  }

  public String getLocalDescription()
  {
    return _localDescription;
  }

  public String getLocalMultiplicity()
  {
    return _localMultiplicty;
  }

  public String getForeignName()
  {
    return _foreignName;
  }

  public String getForeignDescription()
  {
    return _foreignDescription;
  }

  public String getForeignMultiplicity()
  {
    return _foreignMultiplicty;
  }

}
