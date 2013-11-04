package com.seitenbau.stu.io.filter;

import java.io.File;
import java.io.FileFilter;

public class FileNameFilter implements FileFilter
{
  String _filenamePattern;

  public FileNameFilter()
  {
    _filenamePattern = null;
  }

  public FileNameFilter(String pattern)
  {
    _filenamePattern = pattern;
  }

  public boolean accept(File file)
  {
    if (!file.isFile())
    {
      return false;
    }
    if (_filenamePattern == null)
    {
      return true;
    }
    return file.getName().matches(_filenamePattern);
  }

}
