package com.seitenbau.testing.testdata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

public class Filters
{

  public static class NullFilter implements FilterValue
  {

    private final List<String> names;

    public NullFilter()
    {
      this.names = new ArrayList<String>();
    }

    public NullFilter(String... names)
    {
      this.names = Arrays.asList(names);
    }

    public boolean filter(PartitionValue partitionValue)
    {
      String name = partitionValue.getPartition().getParameterDescriptor()
          .getName();
      return partitionValue.getValue() == null && nameMatches(name);
    }

    private boolean nameMatches(String name)
    {
      return names.size() == 0 || names.contains(name);
    }
  }

  public static class DisplayTextStringReplacmentFilter implements
      ReplacementFilter
  {

    public boolean filter(PartitionValue partitionValue)
    {
      if (partitionValue.getValue() instanceof List<?>)
      {
        List<?> list = (List<?>) partitionValue.getValue();
        if (list.size() > 0)
        {
          if (list.get(0) instanceof String)
          {
            return true;
          }
        }
      }
      return partitionValue.getValue() != null
          && partitionValue.getValue().getClass().equals(String.class)
          && ((String) partitionValue.getValue()).startsWith("somexx");
    }

    public Object replace(PartitionValue partitionValue)
    {
      if (partitionValue.getValue() instanceof List<?>)
      {
        ArrayList<String> result = new ArrayList<String>();
        @SuppressWarnings("unchecked")
        List<String> strList = (List<String>) partitionValue.getValue();
        for (String value : strList)
        {
          if (isGeneratedTextValue(value))
          {
            result.add("TEXT(" + value.length() + ")");
          }
          else
          {
            result.add(value);
          }
        }
        return result;
      }
      String strValue = (String) partitionValue.getValue();
      return "TEXT(" + strValue.length() + ")";
    }

  }

  public static class EmptyValueFilter implements FilterValue
  {

    public boolean filter(PartitionValue partitionValue)
    {
      if (partitionValue.getValue() instanceof List<?>)
      {
        List<?> list = (List<?>) partitionValue.getValue();
        return list.isEmpty();
      }
      return StringUtils.isEmpty(ObjectUtils.toString(
          partitionValue.getValue(), "NULL"));
    }
  }

  public static FilterValue nullFilter()
  {
    return new NullFilter();
  }

  public static FilterValue nullFilter(String... names)
  {
    return new NullFilter(names);
  }

  public static ReplacementFilter displayText()
  {
    return new DisplayTextStringReplacmentFilter();
  }

  static boolean isGeneratedTextValue(String value)
  {
    return value != null && value.startsWith("somexx");
  }

}
