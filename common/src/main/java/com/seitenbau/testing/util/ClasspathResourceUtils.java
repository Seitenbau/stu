package com.seitenbau.testing.util;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class ClasspathResourceUtils
{
  /**
   * Return the File Object from the resource in the classpath 
   */
  public static File file4FolderInClasspath(String clazzpathLocationOfFolder)
  {
    try
    {
      URI u = ClasspathResourceUtils.class.getResource(clazzpathLocationOfFolder).toURI();
      String y = u.getPath();
      File root = new File(y);
      return root;
    }
    catch (URISyntaxException e)
    {
      throw new RuntimeException(e);
    }

  }
  
  /**
   * Return the File Object from the resource in the classpath 
   */
  public static File classpathFile(String clazzpathLocationOfFolder)
  {
    try
    {
      URI u = ClasspathResourceUtils.class.getResource(clazzpathLocationOfFolder).toURI();
      String y = u.getPath();
      File root = new File(y);
      return root;
    }
    catch (URISyntaxException e)
    {
      throw new RuntimeException(e);
    }
    
  }
}
