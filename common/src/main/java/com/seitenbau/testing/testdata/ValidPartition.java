package com.seitenbau.testing.testdata;

import java.util.Collection;
import java.util.List;

class ValidPartition extends AbstractPartition
{

  public ValidPartition(ParameterDescriptor property)
  {
    super(property);
  }

  @Override
  void generatePartitionValues(Collection<PartitionValue> partitionValues)
  {
    List<?> values = parameterDesciptor.getValidValues();
    for (Object value : values)
    {
      partitionValues.add(createPartitionValue(value));
    }
    if (parameterDesciptor.isOptional() && !parameterDesciptor.isPrimitiv())
    {
      partitionValues.add(createNullPartitionValue());
      if (String.class.equals(parameterDesciptor.getType()))
      {
        partitionValues.add(createEmptyStringPartitionValue());
      }
    }
    if (partitionValues.size() == 0)
    {
      throw new NoValuesForParameterException("No Valid Values for Parameter "
          + parameterDesciptor.getName());
    }
  }
}
