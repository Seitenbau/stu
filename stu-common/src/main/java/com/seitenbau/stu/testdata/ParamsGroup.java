package com.seitenbau.stu.testdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParamsGroup
{

  private final Class<?> testDataClass;

  final Map<String, ParamValueBuilder> paramValueBuilders = new HashMap<String, ParamValueBuilder>();

  public ParamsGroup(Class<?> testDataClass)
  {
    this.testDataClass = testDataClass;
  }

  public GroupDataBuilder add()
  {
    return new GroupDataBuilder();
  }

  public class GroupDataBuilder
  {

    public ParamValueBuilder param(String name)
    {
      verifyField(name);
      return getParamValueBuilder(name);
    }

    ParamValueBuilder getParamValueBuilder(String name)
    {
      ParamValueBuilder paramValueBuilder = getParamValueBuilders().get(name);
      if (paramValueBuilder == null)
      {
        paramValueBuilder = new ParamValueBuilder(this);
        paramValueBuilders.put(name, paramValueBuilder);
      }
      return paramValueBuilder;
    }

  }
  
  public class ParamValueBuilder
  {

    private final List<Object> values = new ArrayList<Object>();

    private final GroupDataBuilder groupDataBuilder;

    public ParamValueBuilder(GroupDataBuilder groupDataBuilder)
    {
      this.groupDataBuilder = groupDataBuilder;
    }

    public GroupDataBuilder withValue(Object value)
    {
      values.add(value);
      return groupDataBuilder;
    }

    public Object[] getValues()
    {
      return values.toArray();
    }

  }
  
  /**
   * Verify that the field with the name in the test data class
   * exists.
   */
  void verifyField(final String fieldName)
  {
    try
    {
      testDataClass.getField(fieldName);
    }
    catch (Exception exp)
    {
      throw new FieldReadException(exp);
    }
  }

  Map<String, ParamValueBuilder> getParamValueBuilders()
  {
    return paramValueBuilders;
  }

}
