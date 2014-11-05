package com.seitenbau.stu.config.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import com.seitenbau.stu.config.ConfigInjector;
import com.seitenbau.stu.config.OptionalProperty;
import com.seitenbau.stu.config.StoredProperty;
import com.seitenbau.stu.config.ValueProvider;
import com.seitenbau.stu.logger.Logger;
import com.seitenbau.stu.logger.TestLoggerFactory;
import com.seitenbau.stu.util.Holder;

public class BeanConfigInjector implements ConfigInjector
{
  Logger logger = TestLoggerFactory.get(BeanConfigInjector.class);
  
  boolean _failOnNotSet;

  public BeanConfigInjector()
  {
    _failOnNotSet = true;
  }

  public BeanConfigInjector(boolean fastFail)
  {
    _failOnNotSet = fastFail;
  }

  /**
   * Injects the property value into the test configuration instance or into
   * the static fields or a static configuration class.
   *
   * @param values
   *            the property value which should be inject, not null.
   *
   * @param injetInto
   *            Instance of a configuration class or class object with static
   *            fields.
   *
   * @throws NonStaticFieldException
   *             when injetInto is a class and the class has non static fields
   *             with the StoredProperty annotation.
   */
  @Override
  public void injectValuesInto(ValueProvider values, Object injetInto)
  {
    Class<?> target = injetInto.getClass();
    Object instance = injetInto;
    if (injetInto instanceof Class) {
      target = (Class<?>) injetInto;
      instance = null;
    }
    doInjectValues(values, target, instance);
  }

  protected void doInjectValues(ValueProvider values, Class<?> clazz,  Object instance)
  {
    Class<?>[] interfaces = clazz.getInterfaces();
    for(Class<?> one : interfaces )
    {
      doInjectValues(values, one, instance);
    }
    if(clazz.getSuperclass()!=null && clazz.getSuperclass()!=Object.class && clazz!=Object.class)
    {
      doInjectValues(values, clazz.getSuperclass(), instance);
    }

    Field[] fields = clazz.getDeclaredFields();
    for (Field field : fields) {
      StoredProperty anno = field.getAnnotation(StoredProperty.class);
      if (anno == null)
      {
        continue;
      }
      
      boolean isOptional = false;
      OptionalProperty optinal = field.getAnnotation(OptionalProperty.class);
      if(optinal!=null) {
        isOptional = true;
      }

      if (isStaticField(field) && instance == null)
      {
        throw new NonStaticFieldException("Static class configuration "
            + clazz.getName()
            + " initialize with non static field : "
            + field.getName());
      }

      FieldSetter f = new FieldSetter(field);
      Class<?> type = f.getType();
      Object value = transformValue(values,type,anno,field.getName(),isOptional);
      f.setValue(instance,value);
    }
  }

  protected Object transformValue(ValueProvider values, Class<?> type, StoredProperty anno,String errorDetail,boolean isOptional)
  {
    String key = anno.value();
    if(key == null || key.equals(StoredProperty.NOT_SET_VALUE)) {
      key = anno.key();
    }
    System.out.println(key);
    String defaultvalue = StoredProperty.NOT_SET_VALUE;
    Object value = null;
    if (type.isAssignableFrom(Map.class)) {
      value = toMap(values, key);
      if(_failOnNotSet && value == null)
      {
        throw new ConfigValueNotSetException("Unable to find a map for the property : " + key);
      }
    } else {
      String valueAsString = values.getString(key, defaultvalue);
      // TODO : NOT_SET_VALUE should actually be == not equals, the DSL parsing seems to be buggy
      if(_failOnNotSet && StoredProperty.NOT_SET_VALUE.equals(valueAsString))
      {
        value = null;
        if(isOptional) 
        {
          logger.debug("optional property '" + key + "' is not set");
        } else 
        {
          System.err.println("TestConfiguration : Unable to find a value for the property : " + key);
          throw new ConfigValueNotSetException("Unable to find a value for the property : " + key);
        }
      } else {
        value = parseInputType(type, valueAsString, errorDetail);
      }
    }
    return value ;
  }
  
  public static class ConfigValueNotSetException extends RuntimeException {

    public ConfigValueNotSetException(String message)
    {
      super(message);
    }
    
  }

  static class FieldSetter
  {
    Field _field;
    public FieldSetter(Field field )
    {
      _field = field;
    }

    public void setValue(Object instance,Object value)
    {
      Class<?> type = _field.getType();
      if(type.isAssignableFrom(Holder.class))
      {
        setFieldHolderValue(instance,_field, value);
      } else {
        setFieldValue(instance,_field, value);
      }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    protected void setFieldHolderValue(Object instance,Field field, Object value)
    {
      try
      {
        Holder  curValue = (Holder )field.get(instance);
        if(curValue == null)
        {
          curValue=new Holder();
          setFieldValue(instance, field, curValue);
        }
        curValue.setValue(value);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    protected void setFieldValue(Object instance,Field field, Object value)
    {
      field.setAccessible(true);
      try {
        field.set(instance, value);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }

    }

    public Class<?> getType()
    {
      Class<?> type = _field.getType();
      if(type.isAssignableFrom(Holder.class))
      {
        Type genTyp = _field.getGenericType();
        if(genTyp instanceof ParameterizedType) {
          ParameterizedType pt = (ParameterizedType) genTyp;
          type = (Class<?>) pt.getActualTypeArguments()[0];
        }
      }
      return type;
    }

  }

  private boolean isStaticField(Field field)
  {
    return (field.getModifiers() & Modifier.STATIC) != Modifier.STATIC;
  }

  @SuppressWarnings("unchecked")
  protected Object parseInputType(Class<?> type, String valueAsString,
      String fieldName) {
    if (type.isAssignableFrom(Boolean.class)) {
      return Boolean.parseBoolean(valueAsString);
    }
    if (type.isAssignableFrom(Long.class)) {
      return Long.parseLong(valueAsString);
    }
    if (type.isAssignableFrom(Integer.class)) {
      return Integer.parseInt(valueAsString);
    }
    if (type.isAssignableFrom(String.class)) {
      return valueAsString;
    }

    if (type.isEnum())
    {
      @SuppressWarnings("rawtypes")
      Class<? extends Enum> enumType = type.asSubclass(Enum.class);
      return Enum.valueOf(enumType, valueAsString);
    }
    if (type.isPrimitive())
    {
      System.out.println("ERROR: Configuration Field '" + fieldName
          + "' is a primitive type: " + type);
      throw new RuntimeException(
          "Configuration Fields cannot be primitives! " + fieldName);
    }
    System.out.println("ERROR: Configuration Field '" + fieldName
        + "' is of a unknown Type " + type);
    throw new RuntimeException("Unkown type for field " + fieldName);
  }

  private Map<String, String> toMap(ValueProvider values, String key)
  {
    return values.getMap(key);
  }

}
