package com.seitenbau.testing.io.filter;

import java.io.File;
import java.io.FileFilter;

public class DirFilter implements FileFilter
{
  String _directoryNamePattern;

  public DirFilter()
  {
    _directoryNamePattern = null;
  }

  public DirFilter(String directoryNamePattern)
  {
    _directoryNamePattern = directoryNamePattern;
  }

  public boolean accept(File file)
  {
    if (!file.isDirectory())
    {
      return false;
    }
    if (_directoryNamePattern == null)
    {
      return true;
    }
    return file.getName().matches(_directoryNamePattern);
  }
}
