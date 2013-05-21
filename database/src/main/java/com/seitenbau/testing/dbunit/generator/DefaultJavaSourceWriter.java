package com.seitenbau.testing.dbunit.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DefaultJavaSourceWriter implements JavaSourceWriter
{

  public void write(String into, String pkg, String filename, String content)
  {
    if (filename == null)
    {
      throw new IllegalArgumentException("filename not set by the template");
    }
    String path = into;
    if (pkg != null && pkg.length() > 1)
    {
      path = path + pkg.replace(".", "/") + "/";
      new File(path).mkdirs();
    }
    String fn = path + filename;
    
    if (!(filename.endsWith(".java") || filename.endsWith(".groovy"))) {
      fn += ".java";
    }

    try
    {
      FileWriter out = new FileWriter(fn);
      try
      {
        out.append(content);
      }
      finally
      {
        out.close();
      }
    }
    catch (IOException e)
    {
      // TODO: Exception Handling
      e.printStackTrace();
    }
  }

}
