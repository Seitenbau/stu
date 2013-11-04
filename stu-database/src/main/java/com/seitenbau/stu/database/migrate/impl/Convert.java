package com.seitenbau.stu.database.migrate.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.seitenbau.stu.util.CamelCase;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;

public class Convert
{
  private String _baseDataset;

  public Convert(String fqn_OfBaseClass)
  {
    _baseDataset = fqn_OfBaseClass;
  }

  protected DataSet modelOf(String xmlInClasspath)
  {
    InputStream res = Convert.class.getResourceAsStream(xmlInClasspath);
    DataSet o = (DataSet) getXStream().fromXML(res);

    return o;
  }

  protected DataSet modelOf(File xmlInClasspath) throws FileNotFoundException
  {
    InputStream res = new FileInputStream(xmlInClasspath);

    DataSet o = (DataSet) getXStream().fromXML(res);

    return o;
  }

  protected void dump(DataSet o)
  {
    for (Table table : o.tables)
    {

      System.out.println("Table " + table.name);

      int i = 0;
      Map<Integer, String> cols = new HashMap<Integer, String>();
      for (String col : table.columns)
      {
        cols.put(i++, col);
      }
      int r = 0;
      for (Row row : table.rows)
      {
        int c = 0;
        System.out.print("  Row " + r + " = { ");
        for (String value : row.values)
        {
          System.out.print(cols.get(c));
          System.out.print(" = ");
          if (StringUtils.isNumeric(value))
          {
            System.out.print(value);
          }
          else
          {
            System.out.print("\"" + value + "\"");
          }
          System.out.print(" | ");
          c++;
        }
        System.out.println(" }");
        r++;
      }
    }
  }

  protected XStream getXStream()
  {
    XStream xs = new XStream();

    xs.processAnnotations(DataSet.class);
    xs.processAnnotations(Table.class);
    xs.processAnnotations(Row.class);
    return xs;
  }

  public void toDataset(String xmlInClasspath) throws IOException
  {
    toDataset(xmlInClasspath, "src/test/java");
  }

  public void toDataset(File input, String targetFolder) throws IOException
  {
    try
    {
      DataSet model = modelOf(input);
      toDataset(targetFolder, input.getAbsolutePath(), model);
    }
    catch (XStreamException e)
    {
      System.err.println("Did not transform : " + input);
    }
  }

  @SuppressWarnings("deprecation")
  public void toDataset(String targetFolder, String fullPath, DataSet model) throws IOException
  {
    fullPath = fullPath.replace("\\", "/");
    int lastSlash = fullPath.lastIndexOf("/");
    int lastDot = fullPath.lastIndexOf(".");
    String cname = fullPath.substring(lastSlash + 1, lastDot);
    System.out.println("class = " + cname);
    int lastRes = fullPath.lastIndexOf("resources/") + 10;
    if (lastRes == 9)
    {
      lastRes = fullPath.lastIndexOf("classes/") + 8;
    }
    cname = CamelCase.makeFirstUpperCase(cname);
    String pack = fullPath.substring(lastRes, lastSlash).replace("/", ".");
    System.out.println("package = " + pack);

    StringBuffer sb = new StringBuffer();
    transform(sb, model, pack, cname);

    File folder = new File(targetFolder + "/" + pack.replace(".", "/") + "/");
    folder.mkdirs();
    File file = new File(folder, cname + ".java");
    FileOutputStream fout = new FileOutputStream(file);
    IOUtils.write(sb, fout);
    fout.close();
    System.out.println("Write new DataSet class to  " + file);
  }

  public void toDataset(String xmlInClasspath, String targetFolder) throws IOException
  {
    DataSet model = modelOf(xmlInClasspath);
    URL res = Convert.class.getResource(xmlInClasspath);
    toDataset(targetFolder, res.toExternalForm(), model);
  }

  private void transform(StringBuffer sb, DataSet model, String intoPackage, String ClassName)
  {
    sb.append("package " + intoPackage + ";\r\n");
    sb.append("\r\n");
    sb.append("class " + ClassName + " extends " + _baseDataset + " {\r\n");
    sb.append("  \r\n");

    sb.append("  protected void initDataSet()\r\n");
    sb.append("  {\r\n");

    createInitCode(sb, model.tables);

    sb.append("    \r\n");
    sb.append("  }\r\n");

    sb.append("}\r\n");
  }

  private void createInitCode(StringBuffer sb, List<Table> tables)
  {
    for (Table table : tables)
    {
      if (table.rows == null || table.columns == null)
      {
        continue;
      }
      for (Row row : table.rows)
      {
        sb.append("    table_" + nice(table.name) + "\r\n");
        sb.append("      .insertRow()\r\n");
        createSet(sb, table.columns, row);
        sb.append("      ;\r\n");
      }
    }
  }

  private void createSet(StringBuffer sb, List<String> columns, Row row)
  {
    Iterator<String> iter = columns.iterator();
    for (String val : row.values)
    {
      String col = iter.next();
      String escaped = esc(val);
      sb.append("        .set" + nice(col) + "(" + escaped + ")\r\n");
    }
  }

  private String nice(String col)
  {
    return CamelCase.makeFirstOfBlockUppercase(col);
  }

  private String esc(String val)
  {
    if (val == null)
    {
      return null;
    }
    else if (StringUtils.isNumeric(val))
    {
      return val;
    }
    else if (val.equalsIgnoreCase("true") || val.equalsIgnoreCase("false"))
    {
      return val;
    }
    else
    {
      return "\"" + val + "\"";
    }
  }
}
