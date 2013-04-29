package com.seitenbau.testing.testdata;

import java.util.ArrayList;
import java.util.Collection;

abstract class AbstractPartition implements Partition
{

  private class FieldPartitionValue implements PartitionValue
  {

    private final Partition partition;

    private Object value;

    public FieldPartitionValue(Partition partition, Object value)
    {
      this.partition = partition;
      this.setValue(value);
    }

    public void injectValue(Object target)
    {
      partition.getParameterDescriptor().injectValue(target, getValue());
    }

    public Object getValue()
    {
      return value;
    }

    public Partition getPartition()
    {
      return partition;
    }

    public void setValue(Object value)
    {
      this.value = value;
    }

  }

  protected final ParameterDescriptor parameterDesciptor;

  private final Collection<PartitionValue> partitionValues = new ArrayList<PartitionValue>();

  public AbstractPartition(ParameterDescriptor property)
  {
    this.parameterDesciptor = property;
  }

  abstract void generatePartitionValues(
      Collection<PartitionValue> partitionValues);

  public int count()
  {
    return partitionValues.size();
  }

  public ParameterDescriptor getParameterDescriptor()
  {
    return parameterDesciptor;
  }

  public PartitionIterator iterator()
  {
    if (partitionValues.size() > 0)
    {
      PartitionValue defaultPartitionValue = createDefaultValue(partitionValues);
      return new PartitionIterator(defaultPartitionValue,
          partitionValues.iterator());
    }
    else
    {
      return new PartitionIterator(new NullPartitionValue(),
          partitionValues.iterator());
    }
  }

  public void filter(Filter... filters)
  {
    ArrayList<PartitionValue> removeValues = new ArrayList<PartitionValue>();
    for (Filter filter : filters)
    {
      for (PartitionValue partitionValue : partitionValues)
      {
        if (filter instanceof FilterValue)
        {
          FilterValue filterValue = (FilterValue) filter;
          if (filterValue.filter(partitionValue))
          {
            removeValues.add(partitionValue);
          }
        }
        if (filter instanceof ReplacementFilter)
        {
          ReplacementFilter replacementFilter = (ReplacementFilter) filter;
          if (replacementFilter.filter(partitionValue))
          {
            ((FieldPartitionValue) partitionValue).setValue(replacementFilter
                .replace(partitionValue));
          }
        }

      }
    }
    partitionValues.removeAll(removeValues);
  }

  public PartitionValue createPartitionValue(Object value)
  {
    return new FieldPartitionValue(this, value);
  }

  public PartitionValue createNullPartitionValue()
  {
    return new FieldPartitionValue(this, null);
  }

  public PartitionValue createEmptyStringPartitionValue()
  {
    return new FieldPartitionValue(this, "");
  }

  public void generateValues(Filter... filters)
  {
    parameterDesciptor.setFilters(filters);
    generatePartitionValues(partitionValues);
    if (filters != null)
    {
      filter(filters);
    }
  }

  public void addValues(Object... values)
  {
    for (Object value : values)
    {
      partitionValues.add(createPartitionValue(value));
    }
  }

  static PartitionValue createDefaultValue(
      Collection<PartitionValue> partitionValues)
  {
    if (!partitionValues.isEmpty())
    {
      PartitionValue value = partitionValues.iterator().next();
      if (value != null)
      {
        return value;
      }
    }
    return new NullPartitionValue();
  }
}
