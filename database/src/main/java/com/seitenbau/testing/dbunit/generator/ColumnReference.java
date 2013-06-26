package com.seitenbau.testing.dbunit.generator;

public class ColumnReference
{
  private final Column _foreignColumn;

  private final String _localName;

  private final String _localDescription;

  private final String _localMultiplicties;

  private final String _foreignName;

  private final String _foreignDescription;

  private final String _foreignMultiplicties;

  ColumnReference(Column foreignColumn, String localName, String localDescription, String localMultiplicties, String foreignName,
      String foreignDescription, String foreignMultiplicities)
  {
    _foreignColumn = foreignColumn;
    _localName = localName;
    _localDescription = localDescription;
    _localMultiplicties = localMultiplicties;
    _foreignName = foreignName;
    _foreignDescription = foreignDescription;
    _foreignMultiplicties = foreignMultiplicities;
  }

  public Column getForeignColumn()
  {
    return _foreignColumn;
  }

  public String getLocalName()
  {
    return _localName;
  }

  public String getLocalDescription()
  {
    return _localDescription;
  }

  public String getLocalMultiplicities()
  {
    return _localMultiplicties;
  }

  public String getForeignName()
  {
    return _foreignName;
  }

  public String getForeignDescription()
  {
    return _foreignDescription;
  }

  public String getForeignMultiplicities()
  {
    return _foreignMultiplicties;
  }

}
