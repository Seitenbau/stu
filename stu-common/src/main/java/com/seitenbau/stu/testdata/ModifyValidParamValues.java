package com.seitenbau.stu.testdata;

import org.apache.commons.lang.ClassUtils;

class ModifyValidParamValues extends ModifyGroupValues
{

  public ModifyValidParamValues(Class<?> testDataClass)
  {
    super(testDataClass);
  }

  void invokeModifier()
  {
    if (ClassUtils.isAssignable(testDataClass, ModifierValidValues.class))
    {
      ModifierValidValues modifierInstance = (ModifierValidValues) createModifierInstance(testDataClass);
      modifierInstance.modifyValidValues(this);
    }
  }

}
