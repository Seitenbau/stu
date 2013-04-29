package com.seitenbau.testing.testdata;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.seitenbau.testing.testdata.ParamsGroup.GroupDataBuilder;
import com.seitenbau.testing.testdata.ParamsGroup.ParamValueBuilder;

public abstract class ModifyGroupValues
{

  private final Map<Field, ParameterValuesBuilder> paramsFieldData = new HashMap<Field, ParameterValuesBuilder>();

  protected ParamsGroup paramsGroup;

  protected final Class<?> testDataClass;

  private boolean isBuild = false;

  public ModifyGroupValues(Class<?> testDataClass)
  {
    this.testDataClass = testDataClass;
    this.paramsGroup = new ParamsGroup(testDataClass);
  }

  ParameterValuesBuilder paramName(String fieldName)
  {
    try
    {
      Field field = testDataClass.getField(fieldName);
      ParameterValuesBuilder builder = new ParameterValuesBuilder();
      paramsFieldData.put(field, builder);
      return builder;
    }
    catch (Exception exp)
    {
      throw new FieldReadException("No field with name " + fieldName + "found.",
          exp);
    }
  }

  private static class ParameterValuesBuilder
  {

    private Object[] values;

    private void paramValues(Object... values)
    {
      this.values = values;
    }

  }

  boolean containsField(Field field)
  {
    return paramsFieldData.containsKey(field);
  }

  Object[] getFieldParams(Field field)
  {
   ParameterValuesBuilder parameterValuesBuilder = paramsFieldData.get(field);
   if(parameterValuesBuilder != null)
   {
     return parameterValuesBuilder.values;
   }
   else 
   {
     return new Object[]{};
   }
  }

  void build()
  {
    if (!isBuild)
    {
      invokeModifier();
      Map<String, ParamValueBuilder> paramValueBuilders = paramsGroup.getParamValueBuilders();
      Set<String> paramNames = paramValueBuilders.keySet();
      for (String paramName : paramNames)
      {
        ParamValueBuilder paramValueBuilder = paramValueBuilders.get(paramName);
        Object[] values = paramValueBuilder.getValues();
        paramName(paramName).paramValues(values);
      }
      isBuild = true;
    }
  }

  abstract void invokeModifier();

  void modifyPartition(Field field, Partition partition, Filter[] filters)
  {
    build();
    if (containsField(field))
    {
      partition.addValues(getFieldParams(field));
    }
    else
    {
      partition.generateValues(filters);
    }
  }

  static <T> T createModifierInstance(Class<T> modifierImplClass)
  {
    try
    {
      T modifierObj = (T) modifierImplClass.newInstance();
      return modifierObj;
    }
    catch (Exception exp)
    {
      throw new RuntimeException("Could not create instace of "
          + modifierImplClass, exp);
    }
  }

  int maxGroupParamCount()
  {
    int max = 0;
    Map<String, ParamValueBuilder> paramValueBuilders = paramsGroup
        .getParamValueBuilders();
    Set<String> keySet = paramValueBuilders.keySet();
    for (String name : keySet)
    {
      ParamValueBuilder paramValueBuilder = paramValueBuilders.get(name);
      int length = paramValueBuilder.getValues().length;
      if (max < length)
      {
        max = length;
      }
    }
    return max;
  }
  
  public GroupDataBuilder add()
  {
    return paramsGroup.add();
  }

}
