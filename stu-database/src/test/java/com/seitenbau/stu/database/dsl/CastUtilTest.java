package com.seitenbau.stu.database.dsl;

import static org.fest.assertions.Assertions.assertThat;

import org.fest.assertions.Fail;
import org.junit.Test;

import com.seitenbau.stu.database.dsl.CastUtil;
import com.seitenbau.stu.database.generator.DataType;

public class CastUtilTest
{
  @Test
  public void validCastToIntegerWithoutType()
  {
    // prepare
    Object value = 5;

    // execute
    Object castedObject = CastUtil.cast(value, null);

    // verify
    assertThat(castedObject.getClass()).isEqualTo(Integer.class);
  }

  @Test
  public void validCastToInteger()
  {
    // prepare
    Object value = 5;
    DataType type = DataType.INTEGER;

    // execute
    Object castedObject = CastUtil.cast(value, type);

    // verify
    assertThat(castedObject.getClass()).isEqualTo(Integer.class);
  }

  @Test
  public void validCastLongToInteger()
  {
    // prepare
    Long value = 5L;
    DataType type = DataType.INTEGER;

    // execute
    Object castedObject = CastUtil.cast(value, type);

    // verify
    assertThat(castedObject.getClass()).isEqualTo(Integer.class);
  }

  @Test
  public void validCastIntegerToLong()
  {
    // prepare
    Integer value = 5;
    DataType type = DataType.BIGINT;

    // execute
    Object castedObject = CastUtil.cast(value, type);

    // verify
    assertThat(castedObject.getClass()).isEqualTo(Long.class);
  }

  @Test
  public void validCastFloatToDouble()
  {
    // prepare
    Object value = new Float(5.5);
    DataType type = DataType.FLOAT;

    // execute
    Object castedObject = CastUtil.cast(value, type);

    // verify
    assertThat(castedObject.getClass()).isEqualTo(Double.class);
  }

  @Test
  public void validCastIntegerToDouble()
  {
    // prepare
    Object value = new Integer(5);
    DataType type = DataType.FLOAT;

    // execute
    Object castedObject = CastUtil.cast(value, type);

    // verify
    assertThat(castedObject.getClass()).isEqualTo(Double.class);
  }

  @Test
  public void validCastLongToDouble()
  {
    // prepare
    Object value = new Long(5L);
    DataType type = DataType.FLOAT;

    // execute
    Object castedObject = CastUtil.cast(value, type);

    // verify
    assertThat(castedObject.getClass()).isEqualTo(Double.class);
  }

  @Test
  public void validCastStringToDouble()
  {
    // prepare
    Object value = new String("5");
    DataType type = DataType.FLOAT;

    // execute
    Object castedObject = CastUtil.cast(value, type);

    // verify
    assertThat(castedObject.getClass()).isEqualTo(Double.class);
  }
  
  @Test
  public void castNonPrimitiveTypeToDouble()
  {
    // prepare
    ComplexType value = new ComplexType("key", 5);
    DataType type = DataType.FLOAT;

    // execute
    Object castedObject = CastUtil.cast(value, type);

    // verify
    assertThat(castedObject.getClass()).isEqualTo(ComplexType.class);
  }
  
  @Test
  public void castIntegerToBlob()
  {
    // prepare
    Integer value = 5;
    DataType type = DataType.BLOB;

    // execute
    Object castedObject = CastUtil.cast(value, type);

    // verify
    assertThat(castedObject.getClass()).isEqualTo(Integer.class);
  }

  @Test(expected = NumberFormatException.class)
  public void castInvalidStringToDouble()
  {
    // prepare
    Object value = new String("A");
    DataType type = DataType.FLOAT;

    // execute
    CastUtil.cast(value, type);

    // verify
    Fail.fail();
  }
}
