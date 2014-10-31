package com.seitenbau.stu.testdata;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ParameterDescriptorFactoryTest
{

  class Customer
  {
    String name;

    int code;

    @ValidValues({"1", "201"})
    @InvalidValues("23")
    int id;

    boolean top;
    
    @Optional
    @ValidValues({"1", "2"})
    @InvalidValues("23")
    List<String> ids;
    
    @ValidValues("1")
    public List<String> notOptionalIds;
    
    List<Object> notsupportedParameter;
    
  }

  ParameterDescriptorFactory propertyFactory;

  @Before
  public void setup()
  {
    this.propertyFactory = new ParameterDescriptorFactory();
  }

  @Test
  public void createPropertyObject() throws Exception
  {
    Customer dummyObject = new Customer();
    Field nameField = dummyObject.getClass().getDeclaredField("name");
    ParameterDescriptor property = propertyFactory.createProperty(nameField);
    property.injectValue(dummyObject, "Christian");
    assertEquals("Christian", dummyObject.name);
  }

  @Test
  public void createPropertyInteger() throws Exception
  {
    Customer dummyObject = new Customer();
    Field codeField = dummyObject.getClass().getDeclaredField("code");
    ParameterDescriptor property = propertyFactory.createProperty(codeField);
    property.injectValue(dummyObject, 23);
    assertEquals(23, dummyObject.code);
  }

  @Test
  public void createPropertyBoolean() throws Exception
  {
    Customer dummyObject = new Customer();
    Field codeField = dummyObject.getClass().getDeclaredField("top");
    ParameterDescriptor property = propertyFactory.createProperty(codeField);
    property.injectValue(dummyObject, false);
    assertEquals(false, dummyObject.top);
  }

  @Test
  public void getValidValuesBoolean() throws Exception
  {
    Customer dummyObject = new Customer();
    Field codeField = dummyObject.getClass().getDeclaredField("top");
    ParameterDescriptor property = propertyFactory.createProperty(codeField);
    assertEquals(true, property.getValidValues().get(0));
    assertEquals(false, property.getValidValues().get(1));
  }
  
  @Test
  public void getInvalidValuesBoolean() throws Exception
  {
    Customer dummyObject = new Customer();
    Field codeField = dummyObject.getClass().getDeclaredField("top");
    ParameterDescriptor property = propertyFactory.createProperty(codeField);
    assertEquals(Collections.EMPTY_LIST, property.getInvalidValues());
  }


  @Test
  public void getValidValuesIntegerDefault() throws Exception
  {
    Customer dummyObject = new Customer();
    Field codeField = dummyObject.getClass().getDeclaredField("code");
    ParameterDescriptor property = propertyFactory.createProperty(codeField);
    assertEquals(Integer.MIN_VALUE, property.getValidValues().get(0));
    assertEquals(0, property.getValidValues().get(1));
    assertEquals(Integer.MAX_VALUE, property.getValidValues().get(2));
  }

  @Test
  public void getValidValuesInteger() throws Exception
  {
    Customer dummyObject = new Customer();
    Field codeField = dummyObject.getClass().getDeclaredField("id");
    ParameterDescriptor property = propertyFactory.createProperty(codeField);
    assertEquals(1, property.getValidValues().get(0));
    assertEquals(201, property.getValidValues().get(1));
  }
  
  @Test
  public void getInvalidValuesInteger() throws Exception
  {
    Customer dummyObject = new Customer();
    Field codeField = dummyObject.getClass().getDeclaredField("id");
    ParameterDescriptor property = propertyFactory.createProperty(codeField);
    assertEquals(23, property.getInvalidValues().get(0));
  }
  
  @Test
  public void getInvalidValuesIntegerDefault() throws Exception
  {
    Customer dummyObject = new Customer();
    Field codeField = dummyObject.getClass().getDeclaredField("code");
    ParameterDescriptor property = propertyFactory.createProperty(codeField);
    assertEquals(Collections.EMPTY_LIST, property.getInvalidValues());
  }
  
  
  @Test
  public void getValidValuesListOfStrings() throws Exception
  {
    Customer dummyObject = new Customer();
    Field codeField = dummyObject.getClass().getDeclaredField("ids");
    ParameterDescriptor property = propertyFactory.createProperty(codeField);
    
    assertEquals(Arrays.asList(new String[]{}), property.getValidValues().get(0));
    assertEquals(Arrays.asList(new String[]{"1"}), property.getValidValues().get(1));
    assertEquals(Arrays.asList(new String[]{"1", "2"}), property.getValidValues().get(2));
  }
  
  @Test
  public void getInvalidValuesListOfStrings() throws Exception
  {
    Customer dummyObject = new Customer();
    Field codeField = dummyObject.getClass().getDeclaredField("ids");
    ParameterDescriptor property = propertyFactory.createProperty(codeField);
    
    assertEquals(Arrays.asList(new String[]{"23"}), property.getInvalidValues().get(0));
  }
  
  @Test
  public void getInvalidValuesListOfStringsNotOptional() throws Exception
  {
    Customer dummyObject = new Customer();
    Field codeField = dummyObject.getClass().getDeclaredField("notOptionalIds");
    ParameterDescriptor property = propertyFactory.createProperty(codeField);
    
    assertEquals(Collections.EMPTY_LIST, property.getInvalidValues().get(0));
  }
  

}
