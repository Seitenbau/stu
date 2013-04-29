package com.seitenbau.testing.testdata;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ParameterDescriptorFactory
{

  Map<Class<?>, Class<? extends ParameterDescriptor>> typeToParameterDescriptor = 
      new HashMap<Class<?>, Class<? extends ParameterDescriptor>>();
  
  ParameterDescriptor createProperty(Field field)
  {
    if(typeToParameterDescriptor.containsKey(field.getType()))
    {
      Class<? extends ParameterDescriptor> parameterDescriptorClass = typeToParameterDescriptor.get(field.getType());
      try
      {
        Constructor<? extends ParameterDescriptor> constructor = parameterDescriptorClass.getConstructor(Field.class);
        ParameterDescriptor instance = constructor.newInstance(field);
        return instance;
      }
      catch (Exception e)
      {
        throw new InitiateParameterDescriptorException(parameterDescriptorClass);
      }
    }
    else if(field.getType().isAnnotationPresent(Date.class)){
      return new DateParameterDescriptor(field);
    }
    else if (field.getType().equals(boolean.class))
    {
      return new BooleanParameterDescriptor(field);
    }
    else if (field.getType().equals(int.class))
    {
      return new IntegerParameterDescriptor(field);
    }
    else if (field.getType().equals(List.class))
    {
      ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
      Class<?> listClass = (Class<?>) parameterizedType.getActualTypeArguments()[0];
      if (listClass.equals(String.class))
      {
        return new StringListParameterDescriptor(field);
      }
      else
      {
        return new ObjectListParameterDescriptor(field);
      }
    }
    else if(field.getType().equals(String.class))
    {
      return new StringParameterDescriptor(field);
    }
    else if(field.getType().equals(Index.class))
    {
      return new NullParameterDescriptor();
    }
    else
    {
      return new ObjectParameterDescriptor(field);
    }
  }

}
