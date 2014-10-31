package com.seitenbau.stu.data.specs;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.seitenbau.stu.data.detail.Representant;
import com.seitenbau.stu.data.detail.Representant.RepresentantType;

public class OfTypeIntegerTest extends OfTypeTestBase
{
  private OfTypeInteger sut = new OfTypeInteger();

  @Test
  public void withoutParametersValid() throws Exception
  {
    List<Representant<Integer>> all = sut.getRepresentatives(RepresentantType.VALID);
    assertEquals(4, all.size());
    assertThat(all, hasItem(valid((Integer) null)));
    assertThat(all, hasItem(valid(0)));
    assertThat(all, hasItem(valid(Integer.MAX_VALUE)));
    assertThat(all, hasItem(valid(Integer.MIN_VALUE)));
  }

  @Test
  public void withoutParametersInvalid() throws Exception
  {
    List<Representant<Integer>> all = sut.getRepresentatives(RepresentantType.INVALID);
    assertEquals(0, all.size());
  }

  @Test
  public void maxDigitsParametersValid() throws Exception
  {
    List<Representant<Integer>> all = sut.maxDigitLength(4).getRepresentatives(
        RepresentantType.VALID);

    assertEquals(4, all.size());
    assertThat(all, hasItem(valid((Integer) null)));
    assertThat(all, hasItem(valid(1)));
    assertThat(all, hasItem(valid(9999)));
    assertThat(all, hasItem(valid(-9999)));
  }

  @Test
  public void maxDigitsParametersInValid() throws Exception
  {
    List<Representant<Integer>> all = sut.maxDigitLength(4).getRepresentatives(
        RepresentantType.INVALID);

    assertEquals(2, all.size());
    assertThat(all, hasItem(invalid(10000)));
    assertThat(all, hasItem(invalid(-10000)));
  }
  
  @Test
  public void maxDigitsParametersInValidAndNotNegativ() throws Exception
  {
    sut.maxDigitLength(4);
    sut.notNegative();
    List<Representant<Integer>> all = sut.getRepresentatives(
        RepresentantType.INVALID);
    
    assertEquals(1, all.size());
    assertThat(all, hasItem(invalid(10000)));
  }

  @Test
  public void minMaxDigitsParametersValid() throws Exception
  {
    List<Representant<Integer>> all = sut.digitLength(1, 3).getRepresentatives(
        RepresentantType.VALID);
    assertEquals(5, all.size());
    assertThat(all, hasItem(valid((Integer) null)));
    assertThat(all, hasItem(valid(1)));
    assertThat(all, hasItem(valid(-1)));
    assertThat(all, hasItem(valid(999)));
    assertThat(all, hasItem(valid(-999)));
  }

  @Test
  public void minMaxDigitsParametersInValid() throws Exception
  {
    List<Representant<Integer>> all = sut.digitLength(1, 3).getRepresentatives(
        RepresentantType.INVALID);
    assertEquals(2, all.size());
    assertThat(all, hasItem(invalid(1000)));
    assertThat(all, hasItem(invalid(-1000)));
  }

  @Test
  public void minMaxDigitsParametersNotNegativValid() throws Exception
  {
    List<Representant<Integer>> all = sut.digitLength(1, 3).notNegative()
        .getRepresentatives(RepresentantType.VALID);
    assertEquals(3, all.size());
    assertThat(all, hasItem(valid((Integer) null)));
    assertThat(all, hasItem(valid(1)));
    assertThat(all, hasItem(valid(999)));
  }

  @Test
  public void minMaxDigitsParametersInNotNegativInValid() throws Exception
  {
    List<Representant<Integer>> all = sut.digitLength(1, 3).notNegative()
        .getRepresentatives(RepresentantType.INVALID);
    assertEquals(2, all.size());
    assertThat(all, hasItem(invalid(-1)));
    assertThat(all, hasItem(invalid(1000)));
  }
}
