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
    @BeforeDate("gueltigkeitsende")
    @ExcludeDefaultInvalidDateValues
    public DateField gueltigkeitsbeginn;

    @ValidValues({"21.1.2012", "21.1.2009"})
    @ExcludeDefaultInvalidDateValues
    public DateField gueltigkeitsende;

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

    assertEquals("20", validParameters.get(0).gueltigkeitsbeginn.tag);
    assertEquals("01", validParameters.get(0).gueltigkeitsbeginn.monat);
    assertEquals("2011", validParameters.get(0).gueltigkeitsbeginn.jahr);

    assertEquals("21", validParameters.get(0).gueltigkeitsende.tag);
    assertEquals("1", validParameters.get(0).gueltigkeitsende.monat);
    assertEquals("2012", validParameters.get(0).gueltigkeitsende.jahr);

    assertEquals("20", validParameters.get(1).gueltigkeitsbeginn.tag);
    assertEquals("02", validParameters.get(1).gueltigkeitsbeginn.monat);
    assertEquals("2011", validParameters.get(1).gueltigkeitsbeginn.jahr);

    assertEquals("21", validParameters.get(1).gueltigkeitsende.tag);
    assertEquals("1", validParameters.get(1).gueltigkeitsende.monat);
    assertEquals("2009", validParameters.get(1).gueltigkeitsende.jahr);
  }

  @Test
  public void testGetInvalidParameters_ExcludeDefaultInvalidDateValues()
      throws Exception
  {
    List<TestDataExcludeDefaultInvalidDateValues> invalidParameters = generator
        .getInvalidParameters(TestDataExcludeDefaultInvalidDateValues.class);
    assertEquals(1, invalidParameters.size());

    assertEquals("21", invalidParameters.get(0).gueltigkeitsbeginn.tag);
    assertEquals("1", invalidParameters.get(0).gueltigkeitsbeginn.monat);
    assertEquals("2013", invalidParameters.get(0).gueltigkeitsbeginn.jahr);

    assertEquals("21", invalidParameters.get(0).gueltigkeitsende.tag);
    assertEquals("1", invalidParameters.get(0).gueltigkeitsende.monat);
    assertEquals("2012", invalidParameters.get(0).gueltigkeitsende.jahr);
  }
  
  static class TestData
  {

    @ValidValues({"20.01.2011", "20.02.2011"})
    @BeforeDate("gueltigkeitsende")
    public DateField gueltigkeitsbeginn;

    @ValidValues({"21.1.2012", "21.1.2009"})
    public DateField gueltigkeitsende;

  }
  
  @Test
  public void testGetInvalidParameters() throws Exception
  {
    List<TestData> invalidParameters = generator
        .getInvalidParameters(TestData.class);
    assertEquals(11, invalidParameters.size());

    assertEquals("21",    invalidParameters.get(0).gueltigkeitsbeginn.tag);
    assertEquals("1",     invalidParameters.get(0).gueltigkeitsbeginn.monat);
    assertEquals("2013",  invalidParameters.get(0).gueltigkeitsbeginn.jahr);
    assertEquals("21",    invalidParameters.get(0).gueltigkeitsende.tag);
    assertEquals("1",     invalidParameters.get(0).gueltigkeitsende.monat);
    assertEquals("2012",  invalidParameters.get(0).gueltigkeitsende.jahr);
    
    assertEquals("29",    invalidParameters.get(1).gueltigkeitsbeginn.tag);
    assertEquals("2",     invalidParameters.get(1).gueltigkeitsbeginn.monat);
    assertEquals("2013",  invalidParameters.get(1).gueltigkeitsbeginn.jahr);
    assertEquals("21",    invalidParameters.get(1).gueltigkeitsende.tag);
    assertEquals("1",     invalidParameters.get(1).gueltigkeitsende.monat);
    assertEquals("2012",  invalidParameters.get(1).gueltigkeitsende.jahr);
    
    assertEquals("31",    invalidParameters.get(2).gueltigkeitsbeginn.tag);
    assertEquals("4",     invalidParameters.get(2).gueltigkeitsbeginn.monat);
    assertEquals("2012",  invalidParameters.get(2).gueltigkeitsbeginn.jahr);
    assertEquals("21",    invalidParameters.get(2).gueltigkeitsende.tag);
    assertEquals("1",     invalidParameters.get(2).gueltigkeitsende.monat);
    assertEquals("2012",  invalidParameters.get(2).gueltigkeitsende.jahr);
    
    assertEquals("",      invalidParameters.get(3).gueltigkeitsbeginn.tag);
    assertEquals("4",     invalidParameters.get(3).gueltigkeitsbeginn.monat);
    assertEquals("2012",  invalidParameters.get(3).gueltigkeitsbeginn.jahr);
    assertEquals("21",    invalidParameters.get(3).gueltigkeitsende.tag);
    assertEquals("1",     invalidParameters.get(3).gueltigkeitsende.monat);
    assertEquals("2012",  invalidParameters.get(3).gueltigkeitsende.jahr);
    
    assertEquals("30",    invalidParameters.get(4).gueltigkeitsbeginn.tag);
    assertEquals("",      invalidParameters.get(4).gueltigkeitsbeginn.monat);
    assertEquals("2012",  invalidParameters.get(4).gueltigkeitsbeginn.jahr);
    assertEquals("21",    invalidParameters.get(4).gueltigkeitsende.tag);
    assertEquals("1",     invalidParameters.get(4).gueltigkeitsende.monat);
    assertEquals("2012",  invalidParameters.get(4).gueltigkeitsende.jahr);
    
    assertEquals("30",    invalidParameters.get(5).gueltigkeitsbeginn.tag);
    assertEquals("4",     invalidParameters.get(5).gueltigkeitsbeginn.monat);
    assertEquals("",      invalidParameters.get(5).gueltigkeitsbeginn.jahr);
    assertEquals("21",    invalidParameters.get(5).gueltigkeitsende.tag);
    assertEquals("1",     invalidParameters.get(5).gueltigkeitsende.monat);
    assertEquals("2012",  invalidParameters.get(5).gueltigkeitsende.jahr);
    
    assertEquals("20",    invalidParameters.get(6).gueltigkeitsbeginn.tag);
    assertEquals("01",    invalidParameters.get(6).gueltigkeitsbeginn.monat);
    assertEquals("2011",  invalidParameters.get(6).gueltigkeitsbeginn.jahr);
    assertEquals("29",    invalidParameters.get(6).gueltigkeitsende.tag);
    assertEquals("2",     invalidParameters.get(6).gueltigkeitsende.monat);
    assertEquals("2013",  invalidParameters.get(6).gueltigkeitsende.jahr);
    
    assertEquals("20",    invalidParameters.get(7).gueltigkeitsbeginn.tag);
    assertEquals("01",     invalidParameters.get(7).gueltigkeitsbeginn.monat);
    assertEquals("2011",  invalidParameters.get(7).gueltigkeitsbeginn.jahr);
    assertEquals("31",    invalidParameters.get(7).gueltigkeitsende.tag);
    assertEquals("4",     invalidParameters.get(7).gueltigkeitsende.monat);
    assertEquals("2012",  invalidParameters.get(7).gueltigkeitsende.jahr);
    
    assertEquals("20",    invalidParameters.get(8).gueltigkeitsbeginn.tag);
    assertEquals("01",     invalidParameters.get(8).gueltigkeitsbeginn.monat);
    assertEquals("2011",  invalidParameters.get(8).gueltigkeitsbeginn.jahr);
    assertEquals("",    invalidParameters.get(8).gueltigkeitsende.tag);
    assertEquals("4",     invalidParameters.get(8).gueltigkeitsende.monat);
    assertEquals("2012",  invalidParameters.get(8).gueltigkeitsende.jahr);
    
    assertEquals("20",    invalidParameters.get(9).gueltigkeitsbeginn.tag);
    assertEquals("01",     invalidParameters.get(9).gueltigkeitsbeginn.monat);
    assertEquals("2011",  invalidParameters.get(9).gueltigkeitsbeginn.jahr);
    assertEquals("30",    invalidParameters.get(9).gueltigkeitsende.tag);
    assertEquals("",     invalidParameters.get(9).gueltigkeitsende.monat);
    assertEquals("2012",  invalidParameters.get(9).gueltigkeitsende.jahr);
    
    assertEquals("20",    invalidParameters.get(10).gueltigkeitsbeginn.tag);
    assertEquals("01",     invalidParameters.get(10).gueltigkeitsbeginn.monat);
    assertEquals("2011",  invalidParameters.get(10).gueltigkeitsbeginn.jahr);
    assertEquals("30",    invalidParameters.get(10).gueltigkeitsende.tag);
    assertEquals("4",     invalidParameters.get(10).gueltigkeitsende.monat);
    assertEquals("",  invalidParameters.get(10).gueltigkeitsende.jahr);
  }
  
  static class TestDataInvalidValues
  {

    @ExcludeDefaultInvalidDateValues
    @ValidValues({"21.1.2012", "21.1.2009"})
    @InvalidValues({"21.1.2009", " . . "})
    public DateField gueltigkeitsende;

  }

  @Test
  public void testGetInvalidParameters_TestDataInvalidValues() throws Exception
  {
    List<TestDataInvalidValues> invalidParameters = generator
        .getInvalidParameters(TestDataInvalidValues.class);
    assertEquals(2, invalidParameters.size());
    
    assertEquals("21",    invalidParameters.get(0).gueltigkeitsende.tag);
    assertEquals("1",     invalidParameters.get(0).gueltigkeitsende.monat);
    assertEquals("2009",  invalidParameters.get(0).gueltigkeitsende.jahr);
    
    assertEquals(" ",     invalidParameters.get(1).gueltigkeitsende.tag);
    assertEquals(" ",     invalidParameters.get(1).gueltigkeitsende.monat);
    assertEquals(" ",     invalidParameters.get(1).gueltigkeitsende.jahr);
  }
  
  static class TestDataExcludeBeforeDate
  {

    @ValidValues({"20.01.2011", "20.02.2011"})
    @BeforeDate({"gueltigkeitsende", "gueltigkeitsendeTwo"})
    @ExcludeDefaultInvalidDateValues
    public DateField gueltigkeitsbeginn;

    @ValidValues({"01.01.1999", "21.1.2012", "21.1.2009", "01.02.1999"})
    @ExcludeDefaultInvalidDateValues
    public DateField gueltigkeitsende;
    
    @ValidValues({"19.01.2013", "20.01.2013", "01.01.1999"})
    @ExcludeDefaultInvalidDateValues
    public DateField gueltigkeitsendeTwo;

  }
  
  @Test
  public void testGetInvalidParameters_TestDataExcludeBeforeDate() throws Exception
  {
    List<TestDataExcludeBeforeDate> invalidParameters = generator
        .getInvalidParameters(TestDataExcludeBeforeDate.class);
    assertEquals(1, invalidParameters.size());
    
    assertEquals("20",    invalidParameters.get(0).gueltigkeitsbeginn.tag);
    assertEquals("1",     invalidParameters.get(0).gueltigkeitsbeginn.monat);
    assertEquals("2014",  invalidParameters.get(0).gueltigkeitsbeginn.jahr);
  }
  
}
