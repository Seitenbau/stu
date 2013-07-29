package com.seitenbau.testing.testdata;

import static com.seitenbau.testing.testdata.Filters.displayText;
import static com.seitenbau.testing.testdata.Filters.nullFilter;
import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.seitenbau.testing.testdata.Filters.EmptyValueFilter;
import com.seitenbau.testing.testdata.Filters.NullFilter;
import com.seitenbau.testing.util.StringGenerator;

public class TestParameterGeneratorTest
{

  static class CustomerBasics
  {

    @ValidValues({"Christian", "Martin"})
    public String name;

    @ValidValues("Mustermann")
    @InvalidValues("No Name")
    public String lastname;

    @Optional
    @TextValue(maxLength = 200)
    public String description;

    @Optional
    @ValidValues("070711234")
    public List<String> numbers;

  }

  static class NoValues
  {

    public String id;

  }

  TestParameterGenerator parameterGenerator;

  @Before
  public void setup() throws Exception
  {
    this.parameterGenerator = new TestParameterGenerator();
  }

  @Test(expected = NoValuesForParameterException.class)
  public void testGetValidParameters_NoValuesForParameter() throws Exception
  {
    parameterGenerator.getValidParameters(NoValues.class);
  }

  @Test
  public void testGetValidParameters() throws Exception
  {
    List<CustomerBasics> parameters = parameterGenerator
        .getValidParameters(CustomerBasics.class);
    assertEquals(3, parameters.size());

    assertEquals("Mustermann", parameters.get(0).lastname);
    assertEquals("Mustermann", parameters.get(1).lastname);
    assertEquals("Mustermann", parameters.get(2).lastname);

    assertEquals("Christian", parameters.get(0).name);
    assertEquals("Martin", parameters.get(1).name);
    assertEquals("Christian", parameters.get(2).name);

    assertEquals(200, parameters.get(0).description.length());
    assertEquals(null, parameters.get(1).description);
    assertEquals(0, parameters.get(2).description.length());

    assertEquals(Collections.EMPTY_LIST, parameters.get(0).numbers);
    assertEquals(Arrays.asList(new String[] {"070711234"}),
        parameters.get(1).numbers);
    assertEquals(null, parameters.get(2).numbers);
  }

  @Test
  public void testGetValidParametersWithFilter() throws Exception
  {
    List<CustomerBasics> parameters = parameterGenerator.getValidParameters(
        CustomerBasics.class, nullFilter());
    assertEquals(2, parameters.size());

    assertEquals("Christian", parameters.get(0).name);
    assertEquals("Martin", parameters.get(1).name);

    assertEquals(200, parameters.get(0).description.length());
    assertEquals(0, parameters.get(1).description.length());
  }

  @Test
  public void testGetInvalidParametersWithFilter() throws Exception
  {
    List<CustomerBasics> parameters = parameterGenerator.getInvalidParameters(
        CustomerBasics.class, nullFilter());
    assertEquals(4, parameters.size());

    assertEquals("", parameters.get(0).name);
    assertEquals("Christian", parameters.get(1).name);
    assertEquals("Christian", parameters.get(2).name);
    assertEquals("Christian", parameters.get(3).name);

    assertEquals("Mustermann", parameters.get(0).lastname);
    assertEquals("No Name", parameters.get(1).lastname);
    assertEquals("", parameters.get(2).lastname);
    assertEquals("Mustermann", parameters.get(3).lastname);

    assertEquals(200, parameters.get(0).description.length());
    assertEquals(200, parameters.get(1).description.length());
    assertEquals(200, parameters.get(2).description.length());
    assertEquals(201, parameters.get(3).description.length());
  }

  @Test
  public void testGetInvalidParametersWithFilterAndName() throws Exception
  {
    List<CustomerBasics> parameters = parameterGenerator.getInvalidParameters(
        CustomerBasics.class, nullFilter("name"));
    assertEquals(5, parameters.size());

    assertEquals("", parameters.get(0).name);
    assertEquals("Christian", parameters.get(1).name);
    assertEquals("Christian", parameters.get(2).name);
    assertEquals("Christian", parameters.get(3).name);
    assertEquals("Christian", parameters.get(4).name);

    assertEquals("Mustermann", parameters.get(0).lastname);
    assertEquals("No Name", parameters.get(1).lastname);
    assertEquals(null, parameters.get(2).lastname);
    assertEquals("", parameters.get(3).lastname);
    assertEquals("Mustermann", parameters.get(4).lastname);

    assertEquals(200, parameters.get(0).description.length());
    assertEquals(200, parameters.get(1).description.length());
    assertEquals(200, parameters.get(2).description.length());
    assertEquals(200, parameters.get(3).description.length());
    assertEquals(201, parameters.get(4).description.length());
  }

  @Test(expected = CanNotInstanceModelClassException.class)
  public void testNewInstance_CanNotInstanceModelClass() throws Exception
  {
    parameterGenerator.newInstance(null);
  }

  static class ListTestData
  {

    @TextValue(maxLength = 50)
    public List<String> textValues;

  }

  @Test
  public void testGetValidParametersListTestData() throws Exception
  {
    List<ListTestData> parameters = parameterGenerator
        .getValidParameters(ListTestData.class);
    assertEquals(2, parameters.size());
    assertEquals(Arrays.asList(new String[] {StringGenerator.gen(50)}),
        parameters.get(0).textValues);
    assertEquals(
        Arrays.asList(new String[] {StringGenerator.gen(50),
            StringGenerator.gen(1)}), parameters.get(1).textValues);
  }

  @Test
  public void testGetInvalidParametersListTestData() throws Exception
  {
    List<ListTestData> parameters = parameterGenerator
        .getInvalidParameters(ListTestData.class);
    assertEquals(3, parameters.size());
    assertEquals(Arrays.asList(new String[] {}), parameters.get(0).textValues);
    assertEquals(Arrays.asList(new String[] {StringGenerator.gen(51)}),
        parameters.get(1).textValues);
    assertEquals(null, parameters.get(2).textValues);
  }

  static class ListTestDataOneToMany
  {

    @TextValue(maxLength = 50, cardinality = Cardinality.OneToMany)
    public List<String> textValues;

  }

  @Test
  public void testGetInvalidParametersListTestDataOneToMany() throws Exception
  {
    List<ListTestDataOneToMany> parameters = parameterGenerator
        .getInvalidParameters(ListTestDataOneToMany.class);
    assertEquals(5, parameters.size());
    assertEquals(Arrays.asList(new String[] {}), parameters.get(0).textValues);
    assertEquals(Arrays.asList(new String[] {StringGenerator.gen(51)}),
        parameters.get(1).textValues);
    assertEquals(Arrays.asList(new String[] {StringGenerator.gen(51), null}),
        parameters.get(2).textValues);
    assertEquals(
        Arrays.asList(new String[] {StringGenerator.gen(51), null, ""}),
        parameters.get(3).textValues);
    assertEquals(null, parameters.get(4).textValues);
  }

  static class ListTestDataZeroToMany
  {

    @TextValue(maxLength = 50, cardinality = Cardinality.ZeroToMany)
    public List<String> textValues;

  }

  @Test
  public void testGetValidParametersListTestDataZeroToMany() throws Exception
  {
    List<ListTestDataZeroToMany> parameters = parameterGenerator
        .getValidParameters(ListTestDataZeroToMany.class);
    assertEquals(4, parameters.size());
    assertEquals(Arrays.asList(new String[] {StringGenerator.gen(50)}),
        parameters.get(0).textValues);
    assertEquals(
        Arrays.asList(new String[] {StringGenerator.gen(50),
            StringGenerator.gen(1)}), parameters.get(1).textValues);
    assertEquals(
        Arrays.asList(new String[] {StringGenerator.gen(50),
            StringGenerator.gen(1), null}), parameters.get(2).textValues);
    assertEquals(
        Arrays.asList(new String[] {StringGenerator.gen(50),
            StringGenerator.gen(1), null, ""}), parameters.get(3).textValues);
  }

  static class TestDataBoolean
  {

    public boolean enable;

  }

  @Test
  public void testGetInvalidParametersTestDataBoolean() throws Exception
  {
    List<TestDataBoolean> parameters = parameterGenerator
        .getInvalidParameters(TestDataBoolean.class);
    assertEquals(0, parameters.size());
  }

  static class TestDataInteger
  {

    public int enable;

  }

  @Test
  public void testGetInvalidParametersTestDataInteger() throws Exception
  {
    List<TestDataInteger> parameters = parameterGenerator
        .getInvalidParameters(TestDataInteger.class);
    assertEquals(0, parameters.size());
  }

  static class Address
  {
    @TextValue(maxLength = 50)
    public String street;
  }

  static class ObjectTestData
  {
    public Address address;
  }

  @Test
  public void testGetValidParametersObjectTestData() throws Exception
  {
    List<ObjectTestData> parameters = parameterGenerator
        .getValidParameters(ObjectTestData.class);
    assertEquals(1, parameters.size());
    assertEquals(StringGenerator.gen(50), parameters.get(0).address.street);
  }

  @Test
  public void testGetInvalidParametersObjectTestData() throws Exception
  {
    List<ObjectTestData> parameters = parameterGenerator
        .getInvalidParameters(ObjectTestData.class);
    assertEquals(4, parameters.size());
    assertEquals(StringGenerator.gen(51), parameters.get(0).address.street);
    assertEquals(null, parameters.get(1).address.street);
    assertEquals("", parameters.get(2).address.street);
    assertEquals(null, parameters.get(3).address);
  }

  @Test
  public void testGetInvalidParametersObjectTestDataWithNullFilter()
      throws Exception
  {
    List<ObjectTestData> parameters = parameterGenerator.getInvalidParameters(
        ObjectTestData.class, nullFilter());
    assertEquals(2, parameters.size());
    assertEquals(StringGenerator.gen(51), parameters.get(0).address.street);
    assertEquals("", parameters.get(1).address.street);
  }

  static class ObjectListTestData
  {
    public List<Address> address;
  }

  @Test
  public void testGetValidParametersObjectListTestData() throws Exception
  {
    List<ObjectListTestData> parameters = parameterGenerator
        .getValidParameters(ObjectListTestData.class);
    assertEquals(1, parameters.size());
    assertEquals(1, parameters.get(0).address.size());
    assertEquals(StringGenerator.gen(50),
        parameters.get(0).address.get(0).street);
  }

  @Test
  public void testGetInvalidParametersObjectListTestData() throws Exception
  {
    List<ObjectListTestData> parameters = parameterGenerator
        .getInvalidParameters(ObjectListTestData.class);
    assertEquals(5, parameters.size());

    assertEquals(StringGenerator.gen(51),
        parameters.get(0).address.get(0).street);
    assertEquals(null, parameters.get(1).address.get(0).street);
    assertEquals("", parameters.get(2).address.get(0).street);

    assertEquals(Collections.EMPTY_LIST, parameters.get(3).address);
    assertEquals(null, parameters.get(4).address);
  }

  static class ObjectListOptionalTestData
  {
    @Optional
    public List<Address> address;
  }

  @Test
  public void testGetValidParametersObjectListOptionalTestData()
      throws Exception
  {
    List<ObjectListOptionalTestData> parameters = parameterGenerator
        .getValidParameters(ObjectListOptionalTestData.class);
    assertEquals(3, parameters.size());
    assertEquals(1, parameters.get(0).address.size());
    assertEquals(StringGenerator.gen(50),
        parameters.get(0).address.get(0).street);
    assertEquals(Collections.EMPTY_LIST, parameters.get(1).address);
    assertEquals(null, parameters.get(2).address);
  }

  @Test
  public void testGetInvalidParametersObjectListOptionalTestData()
      throws Exception
  {
    List<ObjectListOptionalTestData> parameters = parameterGenerator
        .getInvalidParameters(ObjectListOptionalTestData.class);
    assertEquals(3, parameters.size());
    assertEquals(StringGenerator.gen(51),
        parameters.get(0).address.get(0).street);
    assertEquals(null, parameters.get(1).address.get(0).street);
    assertEquals("", parameters.get(2).address.get(0).street);
  }

  @Test
  public void testGetInvalidParametersObjectListOptionalTestData_displayText()
      throws Exception
  {
    List<ObjectListOptionalTestData> parameters = parameterGenerator
        .getInvalidParameters(ObjectListOptionalTestData.class, displayText());
    assertEquals(3, parameters.size());
    assertEquals("TEXT(51)", parameters.get(0).address.get(0).street);
    assertEquals(null, parameters.get(1).address.get(0).street);
    assertEquals("", parameters.get(2).address.get(0).street);
  }

  static class StringListOptionalTestData
  {
    @TextValue(maxLength = 50)
    public List<String> address;
  }

  @Test
  public void testGetValidParametersStringListOptionalTestData()
      throws Exception
  {
    List<StringListOptionalTestData> parameters = parameterGenerator
        .getValidParameters(StringListOptionalTestData.class, displayText());
    assertEquals(2, parameters.size());
    assertEquals("TEXT(50)", parameters.get(1).address.get(0));
  }

  @TestDataFilters({NullFilter.class, EmptyValueFilter.class})
  static class StringListFilterTestData
  {
    @TextValue(maxLength = 50)
    public List<String> address;

    @TextValue(maxLength = 60)
    public String text;
  }

  @Test
  public void testGetInvalidParametersStringListFilterTestData()
      throws Exception
  {
    List<StringListFilterTestData> parameters = parameterGenerator
        .getInvalidParameters(StringListFilterTestData.class, displayText());
    assertEquals(2, parameters.size());
    assertEquals("TEXT(51)", parameters.get(0).address.get(0));
    assertEquals("TEXT(61)", parameters.get(1).text);
  }

  static class IndexedTestData
  {
    public Index index;

    @ValidValues({"tux", "linux", "bsd"})
    @InvalidValues({"pwd", "user", "Mr.X", "007"})
    public String username;

  }

  @Test
  public void testGetValidParameters_IndexedTestData() throws Exception
  {
    List<IndexedTestData> parameters = parameterGenerator
        .getValidParameters(IndexedTestData.class);
    assertEquals(3, parameters.size());
    assertEquals("1", parameters.get(0).index.toString());
    assertEquals("2", parameters.get(1).index.toString());
    assertEquals("3", parameters.get(2).index.toString());
  }

  @Test
  public void testGetInvalidParameters_IndexedTestData() throws Exception
  {
    List<IndexedTestData> parameters = parameterGenerator
        .getInvalidParameters(IndexedTestData.class);
    assertEquals(6, parameters.size());
    assertEquals("1", parameters.get(0).index.toString());
    assertEquals("2", parameters.get(1).index.toString());
    assertEquals("3", parameters.get(2).index.toString());
    assertEquals("4", parameters.get(3).index.toString());
  }

  @Test
  public void testIsValidParameter() throws Exception
  {
    assertTrue(parameterGenerator.isValidParameter(
        IndexedTestData.class.getField("username"), "tux"));
  }

  @Test
  public void testIsNotValidParameter_NULL() throws Exception
  {
    assertFalse(parameterGenerator.isValidParameter(
        IndexedTestData.class.getField("username"), null));
  }

  @Test
  public void testIsNotValidParameter() throws Exception
  {
    assertFalse(parameterGenerator.isValidParameter(
        IndexedTestData.class.getField("username"), "user"));
  }

  static class OptionalTestData
  {
    public Index index;

    @Optional
    @InvalidValues({"pwd", "user", "Mr.X", "007"})
    public String username;

  }

  @Test
  public void testIsValidParameter_Optional() throws Exception
  {
    assertTrue(parameterGenerator.isValidParameter(
        OptionalTestData.class.getField("username"), null));
  }

  @Test
  public void testIsValidParameter_Optional_EmptyString() throws Exception
  {
    assertTrue(parameterGenerator.isValidParameter(
        OptionalTestData.class.getField("username"), ""));
  }

  static class OwnStringParameterDescriptor extends AbstractParameterDescriptor
  {

    public OwnStringParameterDescriptor(Field field)
    {
      super(field);
    }

    public List<?> getValidValues()
    {
      return Collections.singletonList((Object) "VALID");
    }

    public List<?> getInvalidValues()
    {
      return Collections.singletonList((Object) "NOT VALID");
    }

    public boolean isPrimitiv()
    {
      return false;
    }

    @Override
    void doInjectValue(Object target, Object value)
        throws IllegalAccessException
    {
      javaField.set(target, value);
    }

  }

  static class OwnListParameterDescriptor
  {

    public String data;

  }

  @Test
  public void testAddParameterDescriptor_InvalidValues() throws Exception
  {
    parameterGenerator.addParameterDescriptor(
        OwnStringParameterDescriptor.class).forType(String.class);
    List<OwnListParameterDescriptor> parameters = parameterGenerator
        .getInvalidParameters(OwnListParameterDescriptor.class);
    assertEquals("NOT VALID", parameters.get(0).data);
  }

  @Test
  public void testAddParameterDescriptor_ValidValues() throws Exception
  {
    parameterGenerator.addParameterDescriptor(
        OwnStringParameterDescriptor.class).forType(String.class);
    List<OwnListParameterDescriptor> parameters = parameterGenerator
        .getValidParameters(OwnListParameterDescriptor.class);
    assertEquals("VALID", parameters.get(0).data);
  }

  static class NoConstructorParameterDescriptor extends
      AbstractParameterDescriptor
  {

    public NoConstructorParameterDescriptor()
    {
      super(null);
    }

    public List<?> getValidValues()
    {
      return Collections.singletonList((Object) "VALID");
    }

    public List<?> getInvalidValues()
    {
      return Collections.singletonList((Object) "NOT VALID");
    }

    public boolean isPrimitiv()
    {
      return false;
    }

    @Override
    void doInjectValue(Object target, Object value)
        throws IllegalAccessException
    {
      javaField.set(target, value);
    }

  }

  @Test(expected = InitiateParameterDescriptorException.class)
  public void testAddParameterDescriptor_InitiateParameterDescriptorException()
      throws Exception
  {
    parameterGenerator.addParameterDescriptor(
        NoConstructorParameterDescriptor.class).forType(String.class);
    parameterGenerator.getValidParameters(OwnListParameterDescriptor.class);
  }

  static class InvalidValuesWithMetadata
  {

    @ValidValues("Valid Test Data")
    @InvalidValues(values = {@Value(value = "Invalid Test Data", metadata = {@MetaValue(type = "returnCode", value = "0001")})})
    public String data;

  }

  @Test
  public void testGetMetadata_InvalidValuesWithMetadata() throws Exception
  {
    List<InvalidValuesWithMetadata> parameters = parameterGenerator
        .getInvalidParameters(InvalidValuesWithMetadata.class);
    List<String> values = parameterGenerator.getMetadata("returnCode",
        parameters.get(0));
    assertEquals(1, values.size());
    assertEquals("0001", values.get(0));
    values = parameterGenerator.getMetadata("returnCode", parameters.get(1));
    assertEquals(0, values.size());
  }

  static class InvalidStringListValuesWithMetadata
  {

    @ValidValues("Valid Test Data")
    @InvalidValues(values = {
        @Value(value = "Code01", metadata = {@MetaValue(type = "returnCode", value = "0001")}),
        @Value(value = "Code02", metadata = {@MetaValue(type = "returnCode", value = "0002")})})
    @Optional
    public List<String> codes;

  }

  @Test
  public void testGetMetadata_InvalidStringListValuesWithMetadata()
      throws Exception
  {
    List<InvalidStringListValuesWithMetadata> parameters = parameterGenerator
        .getInvalidParameters(InvalidStringListValuesWithMetadata.class);
    List<String> values = parameterGenerator.getMetadata("returnCode",
        parameters.get(0));
    assertEquals(1, values.size());
    assertEquals("0001", values.get(0));
    values = parameterGenerator.getMetadata("returnCode", parameters.get(1));
    assertEquals(2, values.size());
    assertEquals("0001", values.get(0));
    assertEquals("0002", values.get(1));
  }

  static class InvalidIntegerValuesWithMetadata
  {

    @ValidValues("100")
    @InvalidValues(values = {
        @Value(value = "100", metadata = {@MetaValue(type = "returnCode", value = "0001")}),
        @Value(value = "200", metadata = {@MetaValue(type = "returnCode", value = "0002")})})
    public int codes;

  }

  @Test
  public void testGetMetadata_InvalidIntegerValuesWithMetadata()
      throws Exception
  {
    List<InvalidIntegerValuesWithMetadata> parameters = parameterGenerator
        .getInvalidParameters(InvalidIntegerValuesWithMetadata.class);
    List<String> values = parameterGenerator.getMetadata("returnCode",
        parameters.get(0));
    assertEquals(1, values.size());
    assertEquals("0001", values.get(0));
  }

  static class InvalidValuesWithNoMetadata
  {

    @ValidValues("Valid Test Data")
    @InvalidValues(values = {@Value(value = "Invalid Test Data", metadata = {})})
    public String data;

  }

  @Test
  public void testGetMetadata_InvalidValuesWithNoMetadata() throws Exception
  {
    List<InvalidValuesWithNoMetadata> parameters = parameterGenerator
        .getInvalidParameters(InvalidValuesWithNoMetadata.class);
    List<String> values = parameterGenerator.getMetadata("returnCode",
        parameters.get(0));
    assertEquals(0, values.size());
  }

  static class InvalidValuesWithNoValues
  {

    @ValidValues("Valid Test Data")
    @InvalidValues(values = {})
    public String data;

  }

  @Test
  public void testGetMetadata_InvalidValuesWithNoValues() throws Exception
  {
    List<InvalidValuesWithNoValues> parameters = parameterGenerator
        .getInvalidParameters(InvalidValuesWithNoValues.class);
    List<String> values = parameterGenerator.getMetadata("returnCode",
        parameters.get(0));
    assertEquals(0, values.size());
  }

  static class InvalidValuesManyMetadataValues
  {

    @ValidValues("Valid Test Data")
    @InvalidValues(values = {@Value(value = "Invalid Test Data", metadata = {
        @MetaValue(type = "code1", value = "test1"),
        @MetaValue(type = "code2", value = "test2"),
        @MetaValue(type = "code3", value = "test3"),
        @MetaValue(type = "code4", value = "test4")})})
    public String data;

  }

  @Test
  public void testGetMetadata_InvalidValuesManyMetadataValues()
      throws Exception
  {
    List<InvalidValuesManyMetadataValues> parameters = parameterGenerator
        .getInvalidParameters(InvalidValuesManyMetadataValues.class);
    List<String> values = parameterGenerator.getMetadata("returnCode",
        parameters.get(0));
    assertEquals(0, values.size());
  }

  static class ReadField
  {
    public String name = "Tester";
  }

  @Test
  public void testReadField() throws Exception
  {
    assertEquals("Tester", parameterGenerator.readField(
        ReadField.class.getField("name"), new ReadField()));
  }

  @Test(expected = FieldReadException.class)
  public void testReadField_WrongObjectType() throws Exception
  {
    assertEquals("Tester", parameterGenerator.readField(
        ReadField.class.getField("name"), new Object()));
  }

  static class NotEmptyableTestData
  {
    public Index index;

    @ValidValues({"username"})
    @InvalidValues({"02"})
    @NotEmpty
    public String username;

    @ValidValues({"password"})
    @InvalidValues({"02"})
    public String password;

  }

  @Test
  public void testGetInvalidParameters_NotEmptyableTestData() throws Exception
  {
    List<NotEmptyableTestData> parameters = parameterGenerator
        .getInvalidParameters(NotEmptyableTestData.class);
    assertEquals(4, parameters.size());
  }

  static class ComplexTestData
  {
    public List<Document> document;
  }

  static class Document
  {

    @Optional
    @TextValue(maxLength = 30)
    public String documentNumber;

    @NotEmpty
    public DocumentState documentState;

  }

  static class DocumentState
  {

    @NotEmpty
    @ValidValues({"1"})
    @InvalidValues({"5"})
    public String documentState;

  }

  @Test
  public void testGetInvalidParameters_ComplexTestData() throws Exception
  {
    List<ComplexTestData> invalidParameters = parameterGenerator
        .getInvalidParameters(ComplexTestData.class);
    for (ComplexTestData complexTestData : invalidParameters)
    {
      int countInvalidParams = 0;
      List<Document> documents = complexTestData.document;
      if (documents != null)
      {
        for (Document document : documents)
        {
          boolean validParameter = parameterGenerator.isValidParameter(
              Document.class.getField("documentNumber"),
              document.documentNumber);
          if (!validParameter)
          {
            countInvalidParams++;
          }
          String documentState = document.documentState.documentState;
          validParameter = parameterGenerator.isValidParameter(
              DocumentState.class.getField("documentState"), documentState);
          if (!validParameter)
          {
            countInvalidParams++;
          }
        }
      }
      boolean validParameter = parameterGenerator.isValidParameter(
          ComplexTestData.class.getField("document"), complexTestData.document);
      if (!validParameter)
      {
        countInvalidParams++;
      }
      assertEquals("Count of invalid tests param muse be equal to 1.", 1, countInvalidParams);
    }
  }
  
  @Test
  public void testAddAll_NullList() throws Exception
  {
    ArrayList<String> result = new ArrayList<String>();
    TestParameterGenerator.addAll(result, null);
    assertTrue(result.isEmpty());
  }
  
  @Test
  public void testAddAll_List() throws Exception
  {
    ArrayList<String> result = new ArrayList<String>();
    result.add("a");
    TestParameterGenerator.addAll(result, Arrays.asList("b", "c"));
    assertEquals(Arrays.asList("a", "b", "c"), result);
  }

}
