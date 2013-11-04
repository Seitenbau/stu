package com.seitenbau.stu.database.generator;

public class ColumnReference
{
  private final Column _foreignColumn;

  private final String _localName;

  private final String _localDescription;

  private final String _localMultiplicty;

  private final String _foreignName;

  private final String _foreignDescription;

  private final String _foreignMultiplicty;

  ColumnReference(Column foreignColumn, String localName, String localDescription, String localMultiplicty, String foreignName,
      String foreignDescription, String foreignMultiplicity)
  {
    _foreignColumn = foreignColumn;
    _localName = localName;
    _localDescription = localDescription;
    _localMultiplicty = localMultiplicty;
    _foreignName = foreignName;
    _foreignDescription = foreignDescription;
    _foreignMultiplicty = foreignMultiplicity;
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
