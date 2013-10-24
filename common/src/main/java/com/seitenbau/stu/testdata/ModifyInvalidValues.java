package com.seitenbau.stu.testdata;

import org.apache.commons.lang.ClassUtils;

public class ModifyInvalidValues extends ModifyGroupValues
{

  public ModifyInvalidValues(Class<?> testDataClass)
  {
    super(testDataClass);
    paramsGroup = new InvalidParamsGroup(testDataClass);
  }

  public class InvalidParamsGroup extends ParamsGroup
  {

    public InvalidParamsGroup(Class<?> testDataClass)
    {
      super(testDataClass);
      paramsGroup = new ParamsGroup(testDataClass);
    }

    @Override
    public GroupDataBuilder add()
    {
      return new InvalidGroupDataBuilder();
    }

    public class InvalidGroupDataBuilder extends GroupDataBuilder
    {
      @Override
      public ParamValueBuilder param(String name)
      {
        verifyField(name);
        return getParamValueBuilder(name);
      }

      ParamValueBuilder getParamValueBuilder(String name)
      {
        ParamValueBuilder paramValueBuilder = paramValueBuilders.get(name);
        if (paramValueBuilder == null)
        {
          paramValueBuilder = new InvalidParamValueBuilder(this);
          paramValueBuilders.put(name, paramValueBuilder);
        }
        return paramValueBuilder;
      }
    }

    class InvalidParamValueBuilder extends ParamValueBuilder
    {

      public InvalidParamValueBuilder(InvalidGroupDataBuilder groupDataBuilder)
      {
        super(groupDataBuilder);
      }

      @Override
      public GroupDataBuilder withValue(Object value)
      {
        return super.withValue(new String(value.toString()));
      }

    }

  }

  @Override
  void invokeModifier()
  {
    if (ClassUtils.isAssignable(testDataClass, ModifierInvalidValues.class))
    {
      ModifierInvalidValues modifierInstance = (ModifierInvalidValues) createModifierInstance(testDataClass);
      modifierInstance.modifyInvalidValues(this);
    }
  }

}
