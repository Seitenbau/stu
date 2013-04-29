package com.seitenbau.testing.testdata;

import java.util.Collection;
import java.util.List;

class InvalidPartition extends AbstractPartition
{

  public InvalidPartition(ParameterDescriptor property)
  {
    super(property);
  }

  @Override
  void generatePartitionValues(Collection<PartitionValue> partitionValues)
  {
    List<?> values = parameterDesciptor.getInvalidValues();
    for (Object value : values)
    {
      partitionValues.add(createPartitionValue(value));
    }
    if (!parameterDesciptor.isOptional() && 
        !parameterDesciptor.isPrimitiv() &&
        parameterDesciptor.isEmptyable())
    {
      partitionValues.add(createNullPartitionValue());
      if (parameterDesciptor.getType().equals(String.class))
      {
        partitionValues.add(createEmptyStringPartitionValue());
      }
    }
  }
}
