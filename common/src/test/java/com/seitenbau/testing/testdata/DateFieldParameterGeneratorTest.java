package com.seitenbau.testing.testdata;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class DateFieldParameterGeneratorTest
{

  TestParameterGenerator generator;

  static class TestDataExcludeDefaultInvalidDateValues
  {

    @ValidValues({"20.01.2011", "20.02.2011"})
    @BeforeDate("endOfValidityPeriod")
    @ExcludeDefaultInvalidDateValues
    public DateField beginningOfValidityPeriod;

    @ValidValues({"21.1.2012", "21.1.2009"})
    @ExcludeDefaultInvalidDateValues
    public DateField endOfValidityPeriod;

  }

  @Before
  public void setup() throws Exception
  {
    generator = new TestParameterGenerator();
  }

  @Test
  public void testGetValidParameters() throws Exception
  {
    List<TestDataExcludeDefaultInvalidDateValues> validParameters = generator
        .getValidParameters(TestDataExcludeDefaultInvalidDateValues.class);
    assertEquals(2, validParameters.size());

    assertEquals("20", validParameters.get(0).beginningOfValidityPeriod.day);
    assertEquals("01", validParameters.get(0).beginningOfValidityPeriod.month);
    assertEquals("2011", validParameters.get(0).beginningOfValidityPeriod.year);

    assertEquals("21", validParameters.get(0).endOfValidityPeriod.day);
    assertEquals("1", validParameters.get(0).endOfValidityPeriod.month);
    assertEquals("2012", validParameters.get(0).endOfValidityPeriod.year);

    assertEquals("20", validParameters.get(1).beginningOfValidityPeriod.day);
    assertEquals("02", validParameters.get(1).beginningOfValidityPeriod.month);
    assertEquals("2011", validParameters.get(1).beginningOfValidityPeriod.year);

    assertEquals("21", validParameters.get(1).endOfValidityPeriod.day);
    assertEquals("1", validParameters.get(1).endOfValidityPeriod.month);
    assertEquals("2009", validParameters.get(1).endOfValidityPeriod.year);
  }

  @Test
  public void testGetInvalidParameters_ExcludeDefaultInvalidDateValues()
      throws Exception
  {
    List<TestDataExcludeDefaultInvalidDateValues> invalidParameters = generator
        .getInvalidParameters(TestDataExcludeDefaultInvalidDateValues.class);
    assertEquals(1, invalidParameters.size());

    assertEquals("21", invalidParameters.get(0).beginningOfValidityPeriod.day);
    assertEquals("1", invalidParameters.get(0).beginningOfValidityPeriod.month);
    assertEquals("2013", invalidParameters.get(0).beginningOfValidityPeriod.year);

    assertEquals("21", invalidParameters.get(0).endOfValidityPeriod.day);
    assertEquals("1", invalidParameters.get(0).endOfValidityPeriod.month);
    assertEquals("2012", invalidParameters.get(0).endOfValidityPeriod.year);
  }
  
  static class TestData
  {

    @ValidValues({"20.01.2011", "20.02.2011"})
    @BeforeDate("endOfValidityPeriod")
    public DateField beginningOfValidityPeriod;

    @ValidValues({"21.1.2012", "21.1.2009"})
    public DateField endOfValidityPeriod;

  }
  
  @Test
  public void testGetInvalidParameters() throws Exception
  {
    List<TestData> invalidParameters = generator
        .getInvalidParameters(TestData.class);
    assertEquals(11, invalidParameters.size());

    assertEquals("21",    invalidParameters.get(0).beginningOfValidityPeriod.day);
    assertEquals("1",     invalidParameters.get(0).beginningOfValidityPeriod.month);
    assertEquals("2013",  invalidParameters.get(0).beginningOfValidityPeriod.year);
    assertEquals("21",    invalidParameters.get(0).endOfValidityPeriod.day);
    assertEquals("1",     invalidParameters.get(0).endOfValidityPeriod.month);
    assertEquals("2012",  invalidParameters.get(0).endOfValidityPeriod.year);
    
    assertEquals("29",    invalidParameters.get(1).beginningOfValidityPeriod.day);
    assertEquals("2",     invalidParameters.get(1).beginningOfValidityPeriod.month);
    assertEquals("2013",  invalidParameters.get(1).beginningOfValidityPeriod.year);
    assertEquals("21",    invalidParameters.get(1).endOfValidityPeriod.day);
    assertEquals("1",     invalidParameters.get(1).endOfValidityPeriod.month);
    assertEquals("2012",  invalidParameters.get(1).endOfValidityPeriod.year);
    
    assertEquals("31",    invalidParameters.get(2).beginningOfValidityPeriod.day);
    assertEquals("4",     invalidParameters.get(2).beginningOfValidityPeriod.month);
    assertEquals("2012",  invalidParameters.get(2).beginningOfValidityPeriod.year);
    assertEquals("21",    invalidParameters.get(2).endOfValidityPeriod.day);
    assertEquals("1",     invalidParameters.get(2).endOfValidityPeriod.month);
    assertEquals("2012",  invalidParameters.get(2).endOfValidityPeriod.year);
    
    assertEquals("",      invalidParameters.get(3).beginningOfValidityPeriod.day);
    assertEquals("4",     invalidParameters.get(3).beginningOfValidityPeriod.month);
    assertEquals("2012",  invalidParameters.get(3).beginningOfValidityPeriod.year);
    assertEquals("21",    invalidParameters.get(3).endOfValidityPeriod.day);
    assertEquals("1",     invalidParameters.get(3).endOfValidityPeriod.month);
    assertEquals("2012",  invalidParameters.get(3).endOfValidityPeriod.year);
    
    assertEquals("30",    invalidParameters.get(4).beginningOfValidityPeriod.day);
    assertEquals("",      invalidParameters.get(4).beginningOfValidityPeriod.month);
    assertEquals("2012",  invalidParameters.get(4).beginningOfValidityPeriod.year);
    assertEquals("21",    invalidParameters.get(4).endOfValidityPeriod.day);
    assertEquals("1",     invalidParameters.get(4).endOfValidityPeriod.month);
    assertEquals("2012",  invalidParameters.get(4).endOfValidityPeriod.year);
    
    assertEquals("30",    invalidParameters.get(5).beginningOfValidityPeriod.day);
    assertEquals("4",     invalidParameters.get(5).beginningOfValidityPeriod.month);
    assertEquals("",      invalidParameters.get(5).beginningOfValidityPeriod.year);
    assertEquals("21",    invalidParameters.get(5).endOfValidityPeriod.day);
    assertEquals("1",     invalidParameters.get(5).endOfValidityPeriod.month);
    assertEquals("2012",  invalidParameters.get(5).endOfValidityPeriod.year);
    
    assertEquals("20",    invalidParameters.get(6).beginningOfValidityPeriod.day);
    assertEquals("01",    invalidParameters.get(6).beginningOfValidityPeriod.month);
    assertEquals("2011",  invalidParameters.get(6).beginningOfValidityPeriod.year);
    assertEquals("29",    invalidParameters.get(6).endOfValidityPeriod.day);
    assertEquals("2",     invalidParameters.get(6).endOfValidityPeriod.month);
    assertEquals("2013",  invalidParameters.get(6).endOfValidityPeriod.year);
    
    assertEquals("20",    invalidParameters.get(7).beginningOfValidityPeriod.day);
    assertEquals("01",     invalidParameters.get(7).beginningOfValidityPeriod.month);
    assertEquals("2011",  invalidParameters.get(7).beginningOfValidityPeriod.year);
    assertEquals("31",    invalidParameters.get(7).endOfValidityPeriod.day);
    assertEquals("4",     invalidParameters.get(7).endOfValidityPeriod.month);
    assertEquals("2012",  invalidParameters.get(7).endOfValidityPeriod.year);
    
    assertEquals("20",    invalidParameters.get(8).beginningOfValidityPeriod.day);
    assertEquals("01",     invalidParameters.get(8).beginningOfValidityPeriod.month);
    assertEquals("2011",  invalidParameters.get(8).beginningOfValidityPeriod.year);
    assertEquals("",    invalidParameters.get(8).endOfValidityPeriod.day);
    assertEquals("4",     invalidParameters.get(8).endOfValidityPeriod.month);
    assertEquals("2012",  invalidParameters.get(8).endOfValidityPeriod.year);
    
    assertEquals("20",    invalidParameters.get(9).beginningOfValidityPeriod.day);
    assertEquals("01",     invalidParameters.get(9).beginningOfValidityPeriod.month);
    assertEquals("2011",  invalidParameters.get(9).beginningOfValidityPeriod.year);
    assertEquals("30",    invalidParameters.get(9).endOfValidityPeriod.day);
    assertEquals("",     invalidParameters.get(9).endOfValidityPeriod.month);
    assertEquals("2012",  invalidParameters.get(9).endOfValidityPeriod.year);
    
    assertEquals("20",    invalidParameters.get(10).beginningOfValidityPeriod.day);
    assertEquals("01",     invalidParameters.get(10).beginningOfValidityPeriod.month);
    assertEquals("2011",  invalidParameters.get(10).beginningOfValidityPeriod.year);
    assertEquals("30",    invalidParameters.get(10).endOfValidityPeriod.day);
    assertEquals("4",     invalidParameters.get(10).endOfValidityPeriod.month);
    assertEquals("",  invalidParameters.get(10).endOfValidityPeriod.year);
  }
  
  static class TestDataInvalidValues
  {

    @ExcludeDefaultInvalidDateValues
    @ValidValues({"21.1.2012", "21.1.2009"})
    @InvalidValues({"21.1.2009", " . . "})
    public DateField endOfValidityPeriod;

  }

  @Test
  public void testGetInvalidParameters_TestDataInvalidValues() throws Exception
  {
    List<TestDataInvalidValues> invalidParameters = generator
        .getInvalidParameters(TestDataInvalidValues.class);
    assertEquals(2, invalidParameters.size());
    
    assertEquals("21",    invalidParameters.get(0).endOfValidityPeriod.day);
    assertEquals("1",     invalidParameters.get(0).endOfValidityPeriod.month);
    assertEquals("2009",  invalidParameters.get(0).endOfValidityPeriod.year);
    
    assertEquals(" ",     invalidParameters.get(1).endOfValidityPeriod.day);
    assertEquals(" ",     invalidParameters.get(1).endOfValidityPeriod.month);
    assertEquals(" ",     invalidParameters.get(1).endOfValidityPeriod.year);
  }
  
  static class TestDataExcludeBeforeDate
  {

    @ValidValues({"20.01.2011", "20.02.2011"})
    @BeforeDate({"endOfValidityPeriod", "endOfValidityPeriodTwo"})
    @ExcludeDefaultInvalidDateValues
    public DateField beginningOfValidityPeriod;

    @ValidValues({"01.01.1999", "21.1.2012", "21.1.2009", "01.02.1999"})
    @ExcludeDefaultInvalidDateValues
    public DateField endOfValidityPeriod;
    
    @ValidValues({"19.01.2013", "20.01.2013", "01.01.1999"})
    @ExcludeDefaultInvalidDateValues
    public DateField endOfValidityPeriodTwo;

  }
  
  @Test
  public void testGetInvalidParameters_TestDataExcludeBeforeDate() throws Exception
  {
    List<TestDataExcludeBeforeDate> invalidParameters = generator
        .getInvalidParameters(TestDataExcludeBeforeDate.class);
    assertEquals(1, invalidParameters.size());
    
    assertEquals("20",    invalidParameters.get(0).beginningOfValidityPeriod.day);
    assertEquals("1",     invalidParameters.get(0).beginningOfValidityPeriod.month);
    assertEquals("2014",  invalidParameters.get(0).beginningOfValidityPeriod.year);
  }
  
}
