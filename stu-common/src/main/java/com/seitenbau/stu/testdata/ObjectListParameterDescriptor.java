package com.seitenbau.stu.testdata;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class ObjectListParameterDescriptor extends AbstractParameterDescriptor
{

  ObjectListParameterDescriptor(Field field)
  {
    super(field);
  }

  public List<?> getValidValues()
  {
    List<List<?>> result = new ArrayList<List<?>>();
    Class<?> listType = getListType();
    TestParameterGenerator generator = new TestParameterGenerator();
    List<?> validParameters = generator.getValidParameters(listType, filters);
    result.add(validParameters);
    if(isOptional()){
      result.add(new ArrayList<Object>());
    }
    return result;
  }

  public List<?> getInvalidValues()
  {
    List<List<?>> result = new ArrayList<List<?>>();
    Class<?> listType = getListType();
    TestParameterGenerator generator = new TestParameterGenerator();
    List<?> parameters = generator.getInvalidParameters(listType, filters);
    for (Object invalidParam : parameters) {
        ArrayList<Object> invalidParamList = new ArrayList<Object>();
        invalidParamList.add(invalidParam);
        result.add(invalidParamList);
    }
    if(!isOptional()){
      result.add(new ArrayList<Object>());
    }
    return result;
  }

  public boolean isPrimitiv()
  {
    return false;
  }

  @Override
  void doInjectValue(Object target, Object value) throws IllegalAccessException
  {
    javaField.set(target, value);
  }
  
  private Class<?> getListType()
  {
    ParameterizedType parameterizedType = (ParameterizedType) javaField.getGenericType();
    Class<?> listType = (Class<?>) parameterizedType.getActualTypeArguments()[0];
    return listType;
  }

}