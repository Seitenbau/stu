package com.seitenbau.testing.testdata;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TestDataGruppeTest
{
  TestParameterGenerator parameterGenerator;

  @Before
  public void setup() throws Exception
  {
    this.parameterGenerator = new TestParameterGenerator();
  }
  
  static class TestDataGroup implements ModifierValidValues, ModifierInvalidValues
  {
    public String name;

    public String firstname;

    public String lastname;

    public boolean visible;
    
    @ValidValues({
      "001"
    })
    public String code;

    public void modifyValidValues(ModifyGroupValues group)
    {
      group.add()
        .param("name")      .withValue("Mustermann")
        .param("firstname") .withValue("")
        .param("lastname")  .withValue("");
      
      group.add()
        .param("name")      .withValue("")
        .param("firstname") .withValue("Max")
        .param("lastname")  .withValue("Mustermann");
    }

    public void modifyInvalidValues(ModifyGroupValues group)
    {
      group.add()
        .param("name")      .withValue("Mustermann")
        .param("firstname") .withValue("Max")
        .param("lastname")  .withValue("Mustermann");
      
      group.add()
        .param("name")      .withValue("")
        .param("firstname") .withValue("")
        .param("lastname")  .withValue("");
    }
    
  }

  @Test
  public void testGetValidParameters_TestDataGruppe() throws Exception
  {
    List<TestDataGroup> validParameters = parameterGenerator
        .getValidParameters(TestDataGroup.class);
    
    assertEquals("Mustermann", validParameters.get(0).name);
    assertEquals("",           validParameters.get(0).firstname);
    assertEquals("",           validParameters.get(0).lastname);
    assertEquals(true,         validParameters.get(0).visible);
    assertEquals("001",        validParameters.get(0).code);
    
    assertEquals("",           validParameters.get(1).name);
    assertEquals("Max",        validParameters.get(1).firstname);
    assertEquals("Mustermann", validParameters.get(1).lastname);
    assertEquals(false,        validParameters.get(1).visible);
    assertEquals("001",        validParameters.get(1).code);
  }
  
  
  @Test
  public void testGetInvalidParameters_TestDataGruppe() throws Exception
  {
    List<TestDataGroup> invalidParameters = parameterGenerator
        .getInvalidParameters(TestDataGroup.class);
    
    assertEquals("Mustermann", invalidParameters.get(0).name);
    assertEquals("Max",        invalidParameters.get(0).firstname);
    assertEquals("Mustermann", invalidParameters.get(0).lastname);
    assertEquals(true,         invalidParameters.get(0).visible);
    assertEquals("001",        invalidParameters.get(0).code);
    
    assertEquals("",           invalidParameters.get(1).name);
    assertEquals("",           invalidParameters.get(1).firstname);
    assertEquals("",           invalidParameters.get(1).lastname);
    assertEquals(false,        invalidParameters.get(1).visible);
    assertEquals("001",        invalidParameters.get(1).code);
  }
  
  static class TestDataGruppeInvalidFieldName implements ModifierValidValues
  {
    public String dummy;
    
    public void modifyValidValues(ModifyGroupValues modifyParamValues)
    {
      modifyParamValues.add().param("name").withValue(null);
    }
    
  }
  
  @Test(expected= FieldReadException.class)
  public void testGetValidParameters_TestDataGruppeInvalidFieldName() throws Exception
  {
    parameterGenerator
        .getValidParameters(TestDataGruppeInvalidFieldName.class);
  }
  
  @Test
  public void testIsValidParameter_False() throws Exception
  {
    List<TestDataGroup> invalidParameters = parameterGenerator.getInvalidParameters(TestDataGroup.class);
    boolean validParameter = parameterGenerator.isValidParameter(
        TestDataGroup.class.getField("name"), 
        invalidParameters.get(0).name);
    assertFalse(validParameter);
  }
  
  @Test
  public void testIsValidParameter_True() throws Exception
  {
    boolean validParameter = parameterGenerator.isValidParameter(
        TestDataGroup.class.getField("name"), 
        "Mustermann");
    assertTrue(validParameter);
  }
  
}
