package com.seitenbau.testing.testdata;

public class InitiateParameterDescriptorException extends RuntimeException
{

  private static final long serialVersionUID = 1L;

  public InitiateParameterDescriptorException(
      Class<? extends ParameterDescriptor> parameterDescriptorClass)
  {
    super(    "Error on creating a instance of the parameterDescriptor."
            + " The ParameterDescriptor "
            + parameterDescriptorClass
            + " needs a public constructor with parameter of typ java.lang.reflect.Field");
  }

}
