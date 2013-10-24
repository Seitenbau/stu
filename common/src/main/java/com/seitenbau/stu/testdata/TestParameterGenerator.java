package com.seitenbau.stu.testdata;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

public class TestParameterGenerator
{

  private final Map<Field, ModifyInvalidValues> fieldModifyInvalidValues = new HashMap<Field, ModifyInvalidValues>();

  private final ParameterDescriptorFactory propertyFactory = new ParameterDescriptorFactory();

  public <TYP> List<TYP> getValidParameters(Class<TYP> modelClass)
  {
    return getValidParameters(modelClass, new Filter[] {});
  }

  public <TYP> List<TYP> getValidParameters(Class<TYP> modelClass,
      Filter... filter)
  {
    Filter[] filters = getFilters(modelClass, filter);
    PartitionList validPartitions = createValidPartitions(modelClass, filters);
    return getParameters(modelClass, validPartitions,
        validPartitions.maxPartitionCount());
  }

  public <TYP> List<TYP> getInvalidParameters(Class<TYP> modelClass)
  {
    return getInvalidParameters(modelClass, new Filter[] {});
  }

  public <TYP> List<TYP> getInvalidParameters(Class<TYP> modelClass,
      Filter... filters)
  {
    final ArrayList<TYP> invalidParams = new ArrayList<TYP>();
    final Filter[] filterArray = getFilters(modelClass, filters);
    final PartitionList validPartitions = new PartitionList();
    final PartitionList invalidPartitions = createInvalidPartitions(modelClass,
        filterArray);
    final IndexFieldSetter indexFieldSetter = IndexFieldSetter
        .create(modelClass);
    final ModifyInvalidValues modifyInvalidValues = new ModifyInvalidValues(
        modelClass);
    ClassFieldInvoker.forFieldsOf(modelClass).invoke(new FieldInvoker()
    {
      public void invoke(Field field)
      {
        fieldModifyInvalidValues.put(field, modifyInvalidValues);
        Partition partition = createValidPartition(field);
        modifyInvalidValues.modifyPartition(field, partition, filterArray);
        validPartitions.add(partition);
      }
    });
    int groupCount = modifyInvalidValues.maxGroupParamCount();
    List<TYP> parameters = getParameters(modelClass, validPartitions,
        groupCount, indexFieldSetter);
    for (TYP parameterSet : parameters)
    {
      invalidParams.add(parameterSet);
    }
    for (Partition partition : invalidPartitions)
    {
      PartitionIterator it = partition.iterator();
      for (int i = 0; i < partition.count(); i++)
      {
        TYP model = getParameters(modelClass, validPartitions, 1,
            indexFieldSetter).get(0);
        PartitionValue partitionValue = it.next();
        partitionValue.injectValue(model);
        invalidParams.add(model);
      }
    }
    return invalidParams;
  }

  public class ParameterDescriptorForType
  {
    private final Class<? extends ParameterDescriptor> parameterDescriptorClass;

    public ParameterDescriptorForType(
        Class<? extends ParameterDescriptor> descriptorClass)
    {
      this.parameterDescriptorClass = descriptorClass;
    }

    public void forType(Class<?> clazz)
    {
      propertyFactory.typeToParameterDescriptor.put(clazz,
          parameterDescriptorClass);
    }
  }

  public ParameterDescriptorForType addParameterDescriptor(
      Class<? extends ParameterDescriptor> descriptor)
  {
    return new ParameterDescriptorForType(descriptor);
  }

  <TYP> PartitionList createValidPartitions(Class<TYP> modelClass,
      final Filter[] filters)
  {
    final PartitionList validPartitions = new PartitionList();
    final ModifyValidParamValues modifyValues = new ModifyValidParamValues(
        modelClass);
    ClassFieldInvoker.forFieldsOf(modelClass).invoke(new FieldInvoker()
    {
      public void invoke(Field field)
      {
        Partition partition = createValidPartition(field);
        modifyValues.modifyPartition(field, partition, filters);
        validPartitions.add(partition);
      }
    });
    return validPartitions;
  }

  <TYP> List<TYP> getParameters(Class<TYP> modelClass,
      PartitionList partitions, int count)
  {
    return getParameters(modelClass, partitions, count,
        IndexFieldSetter.create(modelClass));
  }

  <TYP> List<TYP> getParameters(Class<TYP> modelClass,
      PartitionList partitions, int count, IndexFieldSetter index)
  {
    List<TYP> combinations = new ArrayList<TYP>();
    ArrayList<PartitionIterator> partitionIterators = new ArrayList<PartitionIterator>();
    for (Partition partition : partitions)
    {
      partitionIterators.add(partition.iterator());
    }
    for (int i = 0; i < count; i++)
    {
      TYP model = newInstance(modelClass);
      index.next(model);
      for (PartitionIterator itr : partitionIterators)
      {
        PartitionValue partitionValue = itr.next();
        partitionValue.injectValue(model);
      }
      combinations.add(model);
    }
    return combinations;
  }

  public boolean isValidParameter(Field field, Object value)
  {
    if (isValueOfInvalidGroup(field, value))
    {
      return false;
    }
    ParameterDescriptor parameterDescriptor = propertyFactory
        .createProperty(field);
    boolean isInvalidValue = parameterDescriptor.getInvalidValues().contains(
        value);
    if (isInvalidValue)
    {
      return false;
    }
    else
    {
      if (parameterDescriptor.isOptional())
      {
        return true;
      }
      return !StringUtils.isEmpty(ObjectUtils.toString(value));
    }
  }

  public List<String> getMetadata(final String metaType, final Object dataSet)
  {
    final ArrayList<String> result = new ArrayList<String>();
    ClassFieldInvoker.forFieldsOf(dataSet.getClass()).invoke(new FieldInvoker()
    {
      public void invoke(Field field)
      {
        ParameterDescriptor parameterDescriptor = propertyFactory.createProperty(field);
        List<String> metadata;
        metadata = parameterDescriptor.getMetadata(metaType, readField(field, dataSet));
        addAll(result, metadata);
      }
      
    });
    return result;
  }

  <TYP> PartitionList createInvalidPartitions(Class<TYP> modelClass,
      final Filter[] filters)
  {
    final PartitionList invalidPartitions = new PartitionList();
    ClassFieldInvoker.forFieldsOf(modelClass).invoke(new FieldInvoker()
    {
      public void invoke(Field field)
      {
        Partition partition = createInvalidPartition(field);
        partition.generateValues(filters);
        invalidPartitions.add(partition);
      }
    });
    return invalidPartitions;
  }

  <TYP> TYP newInstance(Class<TYP> modelClass)
  {
    try
    {
      return modelClass.newInstance();
    }
    catch (Exception exp)
    {
      throw new CanNotInstanceModelClassException(exp);
    }
  }

  Partition createInvalidPartition(Field field)
  {
    return new InvalidPartition(propertyFactory.createProperty(field));
  }

  Partition createValidPartition(Field field)
  {
    return new ValidPartition(propertyFactory.createProperty(field));
  }

  <TYP> Filter[] getFilters(Class<TYP> modelClass, final Filter... filter)
  {
    Filter[] filters = filter;
    TestDataFilters testDataFilters = modelClass
        .getAnnotation(TestDataFilters.class);
    if (testDataFilters != null)
    {
      Filter[] tmpfilters = getFilter(testDataFilters);
      filters = new Filter[tmpfilters.length + filter.length];
      for (int i = 0; i < tmpfilters.length; i++)
      {
        filters[i] = tmpfilters[i];
      }
      for (int i = tmpfilters.length; i < filters.length; i++)
      {
        filters[i] = filter[i - tmpfilters.length];
      }
    }
    return filters;
  }

  Filter[] getFilter(TestDataFilters testDataFilters)
  {
    Class<? extends Filter>[] filterClasses = testDataFilters.value();
    Filter[] filters = new Filter[filterClasses.length];
    int index = 0;
    for (Class<? extends Filter> filterClass : filterClasses)
    {
      filters[index++] = newInstance(filterClass);
    }
    return filters;
  }

  Object readField(Field field, Object data)
  {
    try
    {
      return field.get(data);
    }
    catch (Exception exp)
    {
      throw new FieldReadException(exp);
    }
  }

  private boolean isValueOfInvalidGroup(Field field, Object value)
  {
    ModifyGroupValues groupValues = fieldModifyInvalidValues.get(field);
    if (groupValues != null)
    {
      Object[] fieldValues = groupValues.getFieldParams(field);
      if (ArrayUtils.contains(fieldValues, value))
      {
        for (Object object : fieldValues)
        {
          if (object == value)
          {
            return true;
          }
        }
      }
    }
    return false;
  }
  
  static <T> void addAll(Collection<T> list1, Collection<T> list2)
  {
    if(list2 != null)
    {
      list1.addAll(list2);
    }
  }

}
