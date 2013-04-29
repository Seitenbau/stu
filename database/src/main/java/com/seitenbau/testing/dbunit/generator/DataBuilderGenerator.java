package com.seitenbau.testing.dbunit.generator;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;

import com.seitenbau.testing.templates.VelocityGenerator;

public class DataBuilderGenerator
{
  /** Path to Velocity template used for model class generation. */
  private static final String TEMPLATE = "/templates/db/DataBuilder.vm";

  /** Regular expression for recognizing placeholders. */
  private static final Pattern PLACEHOLDER = Pattern.compile("^\\$\\{(.+)\\}$");

  /** Data set describing the whole database. */
  private final DataSet dataSet;

  /** Data set containing the actual data values. */
  private final IDataSet data;

  /** Generated builder class name. */
  private final String builderClass;

  /** Package of the generated data set class. */
  private final String builderPackage;

  /** Place-holders found in the input data. */
  private final Map<String, String> placeholders;

  /** Dictionary with replacements for literals. */
  private final Map<String, String> replacements;

  /**
   * Constructor.
   * @param model database model
   * @param data data set with data
   * @param pkg package of the generated data set class
   * @param name class name of the generated data set class
   * @param placeholders place-holders found in the data
   * @throws DataSetException
   */
  public DataBuilderGenerator(DatabaseModel model, IDataSet data, String pkg, String name,
      Map<String, String> placeholders) throws DataSetException
  {
    this(model, data, pkg, name, placeholders, new HashMap<String, String>());
  }

  /**
   * Constructor.
   * @param model database model
   * @param data data set with data
   * @param pkg package of the generated data set class
   * @param name class name of the generated data set class
   * @param placeholders place-holders found in the data
   * @param replace literals and their corresponding constant
   *        replacements
   * @throws DataSetException
   */
  public DataBuilderGenerator(DatabaseModel model, IDataSet data, String pkg, String name,
      Map<String, String> placeholders, Map<String, String> replace) throws DataSetException
  {
    this.dataSet = model.getDataSetGenInstance().getDataSet();
    this.data = data;
    this.builderPackage = pkg;
    this.builderClass = name;
    this.placeholders = placeholders;
    this.replacements = replace;
  }

  /**
   * Generate the data set class.
   * @param outputFolder output folder
   * @throws IOException
   * @throws Exception
   */
  public void generate(String outputFolder) throws IOException, Exception
  {
    new VelocityGenerator().executeTemplate(this, TEMPLATE, outputFolder + '/');
  }

  // Accessors used by the Velocity template
  public boolean hasPlaceholders()
  {
    return placeholders.size() > 0;
  }

  public String getClassName()
  {
    return builderClass;
  }

  public String getPackage()
  {
    return builderPackage;
  }

  public String getDataSetClassName()
  {
    return dataSet.getName() + dataSet.getSuffix();
  }

  public String getDataSetClass()
  {
    return dataSet.getPackage() + '.' + getDataSetClassName();
  }

  public List<Table> getTables() throws DataSetException
  {
    return dataSet.getTables();
  }

  public int getLastRowIndex(Table table)
  {
    try
    {
      return data.getTable(table.getName()).getRowCount() - 1;
    }
    catch (DataSetException e)
    {
      e.printStackTrace();
      return -1;
    }
  }

  public boolean hasRows(Table table)
  {
    try
    {
      return data.getTable(table.getName()).getRowCount() > 0;
    }
    catch (DataSetException e)
    {
      return false;
    }
  }

  public String getColumnValue(Table table, Column column, int row)
  {
    try
    {
      Object valueObj = data.getTable(table.getName()).getValue(row, column.getName());
      if (valueObj != null)
      {
        String value = valueObj.toString();
        if (isPlaceholder(value))
        {
          return placeholderToJava(value);
        }
        if (replacements.containsKey(value))
        {
          return replacements.get(value);
        }
        return toJava(valueObj, column.getJavaType());
      }
    }
    catch (DataSetException e)
    {
    }
    return null;
  }

  public boolean isPlaceholder(Table table, Column column, int row)
  {
    try
    {
      Object value = data.getTable(table.getName()).getValue(row, column.getName());
      if (value == null)
      {
        return false;
      }
      return placeholders.containsKey(value.toString());
    }
    catch (DataSetException e)
    {
      return false;
    }
  }

  // static methods
  /**
   * Convert an object to its Java source representation.
   * @param value object to convert
   * @return object as written in Java
   */
  public static String toJava(Object value, String type)
  {
    if (value == null)
    {
      return null;
    }
    if (Boolean.class.getCanonicalName().equals(type))
    {
      return booleanToJava(value);
    }
    if (String.class.getCanonicalName().equals(type))
    {
      return stringToJava((String) value);
    }
    if (BigDecimal.class.getCanonicalName().equals(type))
    {
      return decimalToJava((BigDecimal) value);
    }
    if (byte[].class.getCanonicalName().equals(type))
    {
      return byteArrayToJava((byte[]) value);
    }
    if (Date.class.getCanonicalName().equals(type) || Time.class.getCanonicalName().equals(type)
        || Timestamp.class.getCanonicalName().equals(type))
    {
      return dateToJava((java.util.Date) value, type);
    }
    if (Integer.class.getCanonicalName().equals(type) || Long.class.getCanonicalName().equals(type)
        || Float.class.getCanonicalName().equals(type) || Double.class.getCanonicalName().equals(type))
    {
      return value.toString();
    }

    return null;
  }

  /**
   * Convert a string to its Java source representation.
   * @param value string to convert
   * @return string as written in Java
   */
  public static String stringToJava(String value)
  {
    return '"' + value.replaceAll("\"", "\\\\\"") + '"';
  }

  /**
   * Convert a big decimal to its Java source representation.
   * @param value big decimal to convert
   * @return big decimal as written in Java
   */
  public static String decimalToJava(BigDecimal value)
  {
    return "new java.math.BigDecimal(\"" + value.toString() + "\")";
  }

  /**
   * Convert a date to its Java source representation.
   * @param value date to convert
   * @param type actual date type
   * @return date as written in Java
   */
  public static String dateToJava(java.util.Date value, String type)
  {
    return "new " + type + "(" + value.getTime() + ") /* " + value.toString() + " */";
  }

  /**
   * Convert a boolean value to its Java source representation.
   * @param value boolean value to convert
   * @return boolean value as written in Java
   */
  public static String booleanToJava(Object value)
  {
    String str = value.toString();
    try
    {
      // try to parse value as number
      return Boolean.toString(Integer.parseInt(str) != 0);
    }
    catch (NumberFormatException e)
    {
      return Boolean.toString(Boolean.parseBoolean(str));
    }
  }

  /**
   * Convert a byte array to its Java source representation.
   * @param value byte array to convert
   * @return byte array as written in Java
   */
  public static String byteArrayToJava(byte[] value)
  {
    StringBuilder result = new StringBuilder("new byte[] {");
    if (value.length > 0)
    {
      result.append(value[0]);
      for (int i = 1; i < value.length; i++)
      {
        result.append(',');
        result.append(value[i]);
      }
    }
    result.append('}');
    return result.toString();
  }

  /**
   * Check if the given string is a placeholder.
   * @param value string to check
   * @return {@code true} if string is a placeholder
   */
  public static boolean isPlaceholder(String value)
  {
    return PLACEHOLDER.matcher(value).matches();
  }

  /**
   * Convert a placeholder to its Java source representation.
   * @param value placeholder
   * @return placeholder as written in Java
   */
  public String placeholderToJava(String value)
  {
    if (placeholders.containsKey(value))
    {
      return placeholders.get(value);
    }
    return '"' + value + '"'; // should not happen
  }
}
