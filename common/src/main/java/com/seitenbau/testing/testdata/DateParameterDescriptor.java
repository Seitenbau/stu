package com.seitenbau.testing.testdata;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.seitenbau.testing.testdata.Date.Day;
import com.seitenbau.testing.testdata.Date.Month;
import com.seitenbau.testing.testdata.Date.Year;

public class DateParameterDescriptor extends AbstractParameterDescriptor
{

  Pattern datePattern = Pattern.compile("([^\\.]*).([^\\.]*).([^\\.]*)");

  public DateParameterDescriptor(Field field)
  {
    super(field);
  }

  @Override
  public boolean isEmptyable()
  {
    return false;
  }

  public List<?> getValidValues()
  {
    ArrayList<Object> validValuesList = new ArrayList<Object>();
    ValidValues validValues = javaField.getAnnotation(ValidValues.class);
    if (validValues != null)
    {
      String[] values = validValues.value();
      for (String dateValue : values)
      {
        Matcher matcher = datePattern.matcher(dateValue);
        if (matcher.matches())
        {
          String day = matcher.group(1);
          String month = matcher.group(2);
          String year = matcher.group(3);
          Object value = createValue(day, month, year);
          validValuesList.add(value);
        }
        else
        {
          throw new RuntimeException("Invalid date format for value.");
        }
      }
    }
    return validValuesList;
  }

  public List<?> getInvalidValues()
  {
    Class<?> type = javaField.getDeclaringClass();
    ArrayList<Object> inValidValuesList = new ArrayList<Object>();
    BeforeDate beforeDate = javaField.getAnnotation(BeforeDate.class);
    if (beforeDate != null)
    {
      ArrayList<DateModel> validDates = new ArrayList<DateModel>();
      String[] values = beforeDate.value();
      for (String fieldName : values)
      {
        try
        {
          Field field = type.getField(fieldName);
          DateParameterDescriptor descriptor = new DateParameterDescriptor(
              field);
          List<?> validValues = descriptor.getValidValues();
          for (Object object : validValues)
          {
            DateModel dateModel = DateModel.create(object);
            validDates.add(dateModel);
          }
        }
        catch (Exception exp)
        {
          throw new FieldReadException(exp);
        }
      }
      DateModel max = Collections.max(validDates);
      Object value = createValue(max.day, max.month, max.year + 1);
      inValidValuesList.add(value);
    }
    ExcludeDefaultInvalidDateValues excludeDefaultInvalidDateValues = javaField
        .getAnnotation(ExcludeDefaultInvalidDateValues.class);
    if(excludeDefaultInvalidDateValues == null)
    {
      inValidValuesList.add(createValue(29, 2, 2013));
      inValidValuesList.add(createValue(31, 4, 2012));
      inValidValuesList.add(createValue("", "4", "2012"));
      inValidValuesList.add(createValue("30", "", "2012"));
      inValidValuesList.add(createValue("30", "4", ""));
    }
    InvalidValues invalidValues = javaField.getAnnotation(InvalidValues.class);
    if(invalidValues != null)
    {
      String[] values = invalidValues.value();
      for (String invalidValue : values)
      {
        Matcher matcher = datePattern.matcher(invalidValue);
        if (matcher.matches())
        {
          String day = matcher.group(1);
          String month = matcher.group(2);
          String year = matcher.group(3);
          Object value = createValue(day, month, year);
          inValidValuesList.add(value);
        }
        else
        {
          throw new RuntimeException("Invalid date format for value.");
        }
      }
    }
    return inValidValuesList;
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

  private static class FieldDateValueInjector
  {

    Field field;

    void inject(Object target, String value) throws IllegalAccessException
    {
      field.set(target, value);
    }
  }

  private static class NullFieldDateValueInjector extends
      FieldDateValueInjector
  {

    @Override
    public void inject(Object target, String value)
        throws IllegalAccessException
    {
    }
  }

  private static class DateModel implements Comparable<DateModel>
  {

    int day;

    int month;

    int year;

    static DateModel create(Object obj)
    {
      DateModel model = new DateModel();
      Field[] fields = obj.getClass().getFields();
      String day = "";
      String month = "";
      String year = "";
      for (Field field : fields)
      {
        if (field.isAnnotationPresent(Day.class))
        {
          FieldDateValueInjector fieldDateValueInjector = new FieldDateValueInjector();
          fieldDateValueInjector.field = field;
          try
          {
            day = ObjectUtils.toString(field.get(obj));
          }
          catch (Exception e)
          {
          }
        }
        else if (field.isAnnotationPresent(Month.class))
        {
          FieldDateValueInjector fieldDateValueInjector = new FieldDateValueInjector();
          fieldDateValueInjector.field = field;
          try
          {
            month = ObjectUtils.toString(field.get(obj));
          }
          catch (Exception e)
          {
          }
        }
        else if (field.isAnnotationPresent(Year.class))
        {
          FieldDateValueInjector fieldDateValueInjector = new FieldDateValueInjector();
          fieldDateValueInjector.field = field;
          try
          {
            year = ObjectUtils.toString(field.get(obj));
          }
          catch (Exception e)
          {
          }
        }
      }
      model.day = NumberUtils.toInt(day);
      model.month = NumberUtils.toInt(month);
      model.year = NumberUtils.toInt(year);
      return model;
    }

    public int compareTo(DateModel dateModel)
    {
      if (this.year > dateModel.year)
      {
        return 1;
      }
      else if (this.month > dateModel.month)
      {
        return 1;
      }
      else if (this.day > dateModel.day)
      {
        return 1;
      }
      else if (this.day == dateModel.day && this.month == dateModel.month
          && this.year == dateModel.year)
      {
        return 0;
      }
      else
      {
        return -1;
      }
    }
  }

  private static class DateValueInjector
  {

    FieldDateValueInjector dayInjector = new NullFieldDateValueInjector();

    FieldDateValueInjector monthInjector = new NullFieldDateValueInjector();

    FieldDateValueInjector yearInjector = new NullFieldDateValueInjector();

  }

  private Object createValue(int day, int month, int year)
  {
    return createValue(String.valueOf(day), String.valueOf(month),
        String.valueOf(year));
  }

  private Object createValue(String day, String month, String year)
  {
    Class<?> dateType = javaField.getType();
    Object newDateObject = createNewDateObject();
    DateValueInjector injector = createDateValueInjector(dateType);
    injectValue(injector.dayInjector, newDateObject, day);
    injectValue(injector.monthInjector, newDateObject, month);
    injectValue(injector.yearInjector, newDateObject, year);
    return newDateObject;
  }

  private Object createNewDateObject()
  {
    Class<?> dateType = javaField.getType();
    try
    {
      return dateType.newInstance();
    }
    catch (Exception exp)
    {
      throw new RuntimeException(exp);
    }
  }

  private static void injectValue(FieldDateValueInjector injector,
      Object target, String value)
  {
    try
    {
      injector.inject(target, value);
    }
    catch (IllegalAccessException exp)
    {
      throw new RuntimeException(exp);
    }
  }

  private static DateValueInjector createDateValueInjector(Class<?> dateType)
  {
    DateValueInjector injector = new DateValueInjector();
    Field[] fields = dateType.getFields();
    for (Field field : fields)
    {
      if (field.isAnnotationPresent(Day.class))
      {
        FieldDateValueInjector fieldDateValueInjector = new FieldDateValueInjector();
        fieldDateValueInjector.field = field;
        injector.dayInjector = fieldDateValueInjector;
      }
      else if (field.isAnnotationPresent(Month.class))
      {
        FieldDateValueInjector fieldDateValueInjector = new FieldDateValueInjector();
        fieldDateValueInjector.field = field;
        injector.monthInjector = fieldDateValueInjector;
      }
      else if (field.isAnnotationPresent(Year.class))
      {
        FieldDateValueInjector fieldDateValueInjector = new FieldDateValueInjector();
        fieldDateValueInjector.field = field;
        injector.yearInjector = fieldDateValueInjector;
      }
    }
    return injector;
  }

}
