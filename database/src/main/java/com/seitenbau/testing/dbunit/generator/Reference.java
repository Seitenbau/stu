package com.seitenbau.testing.dbunit.generator;

public class Reference
{
  private final Column _column;
  
  private final String _localName;

  private final String _localDescription;

  private final String _remoteName;

  private final String _remoteDescription;

  private final Integer _remoteMin;

  private final Integer _remoteMax;

  Reference(Column column, String localName, String localDescription, String remoteName, String remoteDescription, Integer remoteMin, Integer remoteMax)
  {
    _column = column;
    _localName = localName;
    _localDescription = localDescription;
    _remoteName = remoteName;
    _remoteDescription = remoteDescription;
    _remoteMin = remoteMin;
    _remoteMax = remoteMax;
  }

  public Column getColumn()
  {
    return _column;
  }
  
  public String getLocalName()
  {
    return _localName;
  }

  public String getLocalDescription()
  {
    return _localDescription;
  }

  public String getRemoteName()
  {
    return _remoteName;
  }

  public String getRemoteDescription()
  {
    return _remoteDescription;
  }

  public Integer getRemoteMin()
  {
    return _remoteMin;
  }

  public Integer getRemoteMax()
  {
    return _remoteMax;
  }

  // Convenience methods for Column access
  public Table getTable()
  {
    return _column.getTable();
  }

  public String getJavaName()
  {
    return _column.getJavaName();
  }

}
