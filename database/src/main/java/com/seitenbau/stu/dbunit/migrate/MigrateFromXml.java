package com.seitenbau.stu.dbunit.migrate;

import java.io.File;
import java.io.IOException;

import com.seitenbau.stu.dbunit.migrate.impl.Convert;

public class MigrateFromXml
{
  public static void createModelfromXml(String xmlInClasspath, String baseClass) throws IOException
  {
    Convert convert = new Convert(baseClass);
    convert.toDataset(xmlInClasspath);
  }

  /**
   * Transforms all .xml files to data set classes.
   * 
   * processFolders("src/test/resources", "com.example.EmptyDataSet",
   * "src/test/java");
   */
  public static void processFolders(String srcFolder, String baseDsClass, String targetFolder) throws IOException
  {
    Convert convert = new Convert(baseDsClass);
    process(convert, srcFolder, targetFolder);
  }

  public static void main(String[] args) throws IOException
  {
    processFolders("src/test/resources", "com.example.EmptyDataSet", "src/test/java");
  }

  public static void process(Convert convert, String start, String target) throws IOException
  {
    File[] faFiles = new File(start).listFiles();
    for (File file : faFiles)
    {
      if (file.getName().matches("^(.*?).xml$"))
      {
        System.out.println(file.getAbsolutePath());
        convert.toDataset(file, target);
      }
      if (file.isDirectory())
      {
        process(convert, file.getAbsolutePath(), target);
      }
    }
  }
}
