package com.seitenbau.stu.data.specs;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.seitenbau.stu.data.detail.Representant;
import com.seitenbau.stu.data.detail.Representant.RepresentantType;
import com.seitenbau.stu.util.StringGenerator;

public class OfTypeStringTest extends OfTypeTestBase
{
  private OfTypeString sut;

  String fill = OfTypeString.DEFAULT_FILL_STRING;

  @Before
  public void setup()
  {
    sut = new OfTypeString();
  }

  @Test
  public void withoutParametersAndFilter() throws Exception
  {
    List<Representant<String>> all = sut.getRepresentatives(null);
    assertEquals(5, all.size());
    assertThat(all, hasItem(valid((String) null)));
    assertThat(all, hasItem(valid("")));
    assertThat(all, hasItem(valid(" ")));
    assertThat(all, hasItem(valid("\t")));
    assertThat(all, hasItem(valid(StringGenerator.gen(fill, 8077))));
  }

  @Test
  public void withoutParametersAndFilterNotNull() throws Exception
  {
    sut.setNullIsAValue(false);
    List<Representant<String>> all = sut.getRepresentatives(null);
    assertEquals(4, all.size());
    assertThat(all, not(hasItem(valid((String) null))));
    assertThat(all, hasItem(valid("")));
    assertThat(all, hasItem(valid(" ")));
    assertThat(all, hasItem(valid("\t")));
    assertThat(all, hasItem(valid(StringGenerator.gen(fill, 8077))));
  }

  @Test
  public void withMinLengthAndFilter() throws Exception
  {
    sut.minLength(5);
    List<Representant<String>> all = sut.getRepresentatives(null);
    assertEquals(7, all.size());
    assertThat(all, hasItem(valid((String) null)));
    assertThat(all, hasItem(valid("")));
    assertThat(all, hasItem(valid(" ")));
    assertThat(all, hasItem(valid("\t")));
    assertThat(all, hasItem(valid(StringGenerator.gen(fill, 8077))));

    assertThat(all, hasItem(invalid(StringGenerator.gen(fill, 4)))); // invalid
    assertThat(all, hasItem(valid(StringGenerator.gen(fill, 5)))); // valid
  }

  @Test
  public void withMinLength() throws Exception
  {
    sut.required(true);
    sut.noEmptyStringCheck();
    sut.setNullIsAValue(false);
    sut.assumeWhitspacesEqalsAnEmptyString(false);
    sut.maxLength(200);
    sut.minLength(1);
    List<Representant<String>> valid = sut
        .getRepresentatives(RepresentantType.VALID);
    List<Representant<String>> invalid = sut
        .getRepresentatives(RepresentantType.INVALID);
    assertEquals(3, valid.size());
    assertEquals(4, invalid.size());

    assertThat(valid, hasItem(valid("J")));
    assertThat(valid, hasItem(valid(StringGenerator.gen(fill, 199))));
    assertThat(valid, hasItem(valid(StringGenerator.gen(fill, 200))));

    assertThat(invalid, hasItem(invalid("")));
    assertThat(invalid, hasItem(invalid(" ")));
    assertThat(invalid, hasItem(invalid("\t")));
    assertThat(invalid, hasItem(invalid(StringGenerator.gen(fill, 201))));
  }

  @Test
  public void withNotEmpty() throws Exception
  {
    sut.required(true);
    sut.noEmptyStringCheck();
    sut.setNullIsAValue(false);
    sut.assumeWhitspacesEqualsAnEmptyString();
    sut.maxLength(200);
    List<Representant<String>> valid = sut
        .getRepresentatives(RepresentantType.VALID);
    List<Representant<String>> invalid = sut
        .getRepresentatives(RepresentantType.INVALID);
    assertEquals(1, valid.size());
    assertEquals(3, invalid.size());

    assertThat(valid, hasItem(valid(StringGenerator.gen(fill, 200))));

    assertThat(invalid, hasItem(invalid(" ")));
    assertThat(invalid, hasItem(invalid("\t")));
    assertThat(invalid, hasItem(invalid(StringGenerator.gen(fill, 201))));
  }

  @Test
  public void withMinLengthAndFilterNotNull() throws Exception
  {
    sut.setNullIsAValue(false);
    sut.minLength(5);
    List<Representant<String>> all = sut.getRepresentatives(null);
    assertEquals(6, all.size());
    assertThat(all, not(hasItem(valid((String) null))));
    assertThat(all, hasItem(valid("")));
    assertThat(all, hasItem(valid(" ")));
    assertThat(all, hasItem(valid("\t")));
    assertThat(all, hasItem(valid(StringGenerator.gen(fill, 8077))));

    assertThat(all, hasItem(invalid(StringGenerator.gen(fill, 4)))); // invalid
    assertThat(all, hasItem(valid(StringGenerator.gen(fill, 5)))); // valid
  }

  @Test
  public void withMaxLengthAndFilter() throws Exception
  {
    sut.maxLength(15);
    List<Representant<String>> all = sut.getRepresentatives(null);
    assertEquals(6, all.size());
    assertThat(all, hasItem((valid((String) null))));
    assertThat(all, hasItem(valid("")));
    assertThat(all, hasItem(valid(" ")));
    assertThat(all, hasItem(valid("\t")));

    assertThat(all, hasItem(valid(StringGenerator.gen(fill, 15)))); // valid
    assertThat(all, hasItem(invalid(StringGenerator.gen(fill, 16)))); // invalid
  }

  @Test
  public void withMaxLengthAndFilterNotNull() throws Exception
  {
    sut.setNullIsAValue(false).maxLength(15);
    List<Representant<String>> all = sut.getRepresentatives(null);
    assertEquals(5, all.size());
    assertThat(all, not(hasItem((valid((String) null)))));
    assertThat(all, hasItem(valid("")));
    assertThat(all, hasItem(valid(" ")));
    assertThat(all, hasItem(valid("\t")));

    assertThat(all, hasItem(valid(StringGenerator.gen(fill, 15)))); // valid
    assertThat(all, hasItem(invalid(StringGenerator.gen(fill, 16)))); // invalid
  }

  @Test
  public void withMinAndMaxLengthAndFilter() throws Exception
  {
    sut.minLength(5);
    sut.maxLength(15);
    List<Representant<String>> all = sut.getRepresentatives(null);
    assertEquals(9, all.size());
    assertThat(all, hasItem(valid((String) null)));
    assertThat(all, hasItem(valid("")));
    assertThat(all, hasItem(valid(" ")));
    assertThat(all, hasItem(valid("\t")));
    // min
    assertThat(all, hasItem(invalid(StringGenerator.gen(fill, 4)))); // invalid
    assertThat(all, hasItem(valid(StringGenerator.gen(fill, 5)))); // valid
    // middle
    assertThat(all, hasItem(valid(StringGenerator.gen(fill, 10)))); // valid
    // max
    assertThat(all, hasItem(valid(StringGenerator.gen(fill, 15)))); // valid
    assertThat(all, hasItem(invalid(StringGenerator.gen(fill, 16)))); // invalid
  }

  @Test
  public void withMinAndMaxLengthAndFilterNotNull() throws Exception
  {
    sut.setNullIsAValue(false);
    sut.minLength(5);
    sut.maxLength(15);
    List<Representant<String>> all = sut.getRepresentatives(null);
    assertEquals(8, all.size());
    assertThat(all, not(hasItem(valid((String) null))));
    assertThat(all, hasItem(valid("")));
    assertThat(all, hasItem(valid(" ")));
    assertThat(all, hasItem(valid("\t")));
    // min
    assertThat(all, hasItem(invalid(StringGenerator.gen(fill, 4)))); // invalid
    assertThat(all, hasItem(valid(StringGenerator.gen(fill, 5)))); // valid
    // middle
    assertThat(all, hasItem(valid(StringGenerator.gen(fill, 10)))); // valid
    // max
    assertThat(all, hasItem(valid(StringGenerator.gen(fill, 15)))); // valid
    assertThat(all, hasItem(invalid(StringGenerator.gen(fill, 16)))); // invalid
  }

  @Test
  public void withOwnEmptyStringValid() throws Exception
  {
    sut.setWhitspaceStrings("rw");
    sut.setEmptyString("-");
    List<Representant<String>> all = sut.getValidRepresentations();
    assertEquals(4, all.size());
    assertThat(all, hasItem(valid((String) null)));
    assertThat(all, hasItem(valid("-")));
    assertThat(all, hasItem(valid("rw")));
    assertThat(all, hasItem(valid(StringGenerator.gen(fill, 8077))));
  }

  @Test
  public void withOwnEmptyStringValidNotNull() throws Exception
  {
    sut.setNullIsAValue(false);
    sut.setWhitspaceStrings("rw");
    sut.setEmptyString("-");
    List<Representant<String>> all = sut.getValidRepresentations();
    assertEquals(3, all.size());
    assertThat(all, not(hasItem(valid((String) null))));
    assertThat(all, hasItem(valid("-")));
    assertThat(all, hasItem(valid("rw")));
    assertThat(all, hasItem(valid(StringGenerator.gen(fill, 8077))));
  }

  @Test
  public void withOwnEmptySpecValid() throws Exception
  {
    sut.setWhitspaceStrings("rw");
    List<Representant<String>> all = sut.getValidRepresentations();
    assertEquals(4, all.size());
    assertThat(all, hasItem(valid((String) null)));
    assertThat(all, hasItem(valid("")));
    assertThat(all, hasItem(valid("rw")));
    assertThat(all, hasItem(valid(StringGenerator.gen(fill, 8077))));
  }

  @Test
  public void withOwnEmptySpecValidNotNull() throws Exception
  {
    sut.setNullIsAValue(false);
    sut.setWhitspaceStrings("rw");
    List<Representant<String>> all = sut.getValidRepresentations();
    assertEquals(3, all.size());
    assertThat(all, not(hasItem(valid((String) null))));
    assertThat(all, hasItem(valid("")));
    assertThat(all, hasItem(valid("rw")));
    assertThat(all, hasItem(valid(StringGenerator.gen(fill, 8077))));
  }

  @Test
  public void withOwnEmptySpecInValid() throws Exception
  {
    sut.setWhitspaceStrings("rw");
    List<Representant<String>> all = sut.getInValidRepresentations();
    assertEquals(0, all.size());
  }

  @Test
  public void withOwnEmptySpecInValidNotNull() throws Exception
  {
    sut.setNullIsAValue(false);
    sut.setWhitspaceStrings("rw");
    List<Representant<String>> all = sut.getInValidRepresentations();
    assertEquals(0, all.size());
  }

  @Test
  public void withOwnEmptySpecInValidAndRequired() throws Exception
  {
    sut.setWhitspaceStrings("rw");
    sut.required(true);
    List<Representant<String>> all = sut.getInValidRepresentations();
    assertEquals(3, all.size());
    assertThat(all, hasItem(invalid((String) null)));
    assertThat(all, hasItem(invalid("")));
    assertThat(all, hasItem(invalid("rw")));
  }

  @Test
  public void withOwnEmptySpecInValidAndRequiredNotNull() throws Exception
  {
    sut.setNullIsAValue(false);
    sut.setWhitspaceStrings("rw");
    sut.required(true);
    List<Representant<String>> all = sut.getInValidRepresentations();
    assertEquals(2, all.size());
    assertThat(all, not(hasItem(invalid((String) null))));
    assertThat(all, hasItem(invalid("")));
    assertThat(all, hasItem(invalid("rw")));
  }

  @Test
  public void withOwnEmptySpecInValidAndRequiredWithoutEmptyString()
      throws Exception
  {
    sut.setWhitspaceStrings("rw");
    sut.noEmptyStringCheck();
    sut.required(true);
    List<Representant<String>> all = sut.getInValidRepresentations();
    assertEquals(2, all.size());
    assertThat(all, hasItem(invalid((String) null)));
    assertThat(all, hasItem(invalid("rw")));
  }

  @Test
  public void withOwnEmptySpecInValidAndRequiredWithoutEmptyStringNotNull()
      throws Exception
  {
    sut.setNullIsAValue(false);
    sut.setWhitspaceStrings("rw");
    sut.noEmptyStringCheck();
    sut.required(true);
    List<Representant<String>> all = sut.getInValidRepresentations();
    assertEquals(1, all.size());
    assertThat(all, not(hasItem(invalid((String) null))));
    assertThat(all, hasItem(invalid("rw")));
  }

  @Test
  public void whitespacesAreNotToTrim() throws Exception
  {
    sut.assumeWhitspacesEqualsAnEmptyString();
    { // valid
      List<Representant<String>> all = sut.getValidRepresentations();
      assertEquals(5, all.size());

      assertThat(all, hasItem(valid((String) null)));
      assertThat(all, hasItem(valid("")));
      assertThat(all, hasItem(valid(" ")));
      assertThat(all, hasItem(valid("\t")));
      assertThat(all, hasItem(valid(StringGenerator.gen(fill, 8077))));
    }
    { // invalid
      List<Representant<String>> all = sut.getInValidRepresentations();
      assertEquals(0, all.size());
    }
  }

  @Test
  public void whitespacesAreNotToTrimNoNull() throws Exception
  {
    sut.assumeWhitspacesEqualsAnEmptyString().setNullIsAValue(false);
    { // valid
      List<Representant<String>> all = sut.getValidRepresentations();
      assertEquals(4, all.size());

      assertThat(all, not(hasItem(valid((String) null))));
      assertThat(all, hasItem(valid("")));
      assertThat(all, hasItem(valid(" ")));
      assertThat(all, hasItem(valid("\t")));
      assertThat(all, hasItem(valid(StringGenerator.gen(fill, 8077))));
    }
    { // invalid
      List<Representant<String>> all = sut.getInValidRepresentations();
      assertEquals(0, all.size());
    }
  }

  @Test
  public void whitespacesAreToTrimOnRequired() throws Exception
  {
    sut.assumeWhitspacesEqualsAnEmptyString().required(false);
    { // valid
      List<Representant<String>> all = sut.getValidRepresentations();
      assertEquals(5, all.size());
      assertThat(all, hasItem(valid((String) null)));
      assertThat(all, hasItem(valid("")));
      assertThat(all, hasItem(valid(" ")));
      assertThat(all, hasItem(valid("\t")));
      assertThat(all, hasItem(valid(StringGenerator.gen(fill, 8077))));
    }
    { // invalid
      List<Representant<String>> all = sut.getInValidRepresentations();
      assertEquals(0, all.size());
    }
  }

  @Test
  public void whitespacesAreToTrimOnRequiredNotNull() throws Exception
  {
    sut.assumeWhitspacesEqualsAnEmptyString().setNullIsAValue(false)
        .required(false);
    { // valid
      List<Representant<String>> all = sut.getValidRepresentations();
      assertEquals(4, all.size());
      assertThat(all, not(hasItem(valid((String) null))));
      assertThat(all, hasItem(valid("")));
      assertThat(all, hasItem(valid(" ")));
      assertThat(all, hasItem(valid("\t")));
      assertThat(all, hasItem(valid(StringGenerator.gen(fill, 8077))));
    }
    { // invalid
      List<Representant<String>> all = sut.getInValidRepresentations();
      assertEquals(0, all.size());
    }
  }

  @Test
  public void whitespacesAreToTrimOnRequiredNoEmptyString() throws Exception
  {
    sut.noEmptyStringCheck();
    sut.assumeWhitspacesEqualsAnEmptyString();
    sut.required(false);
    { // valid
      List<Representant<String>> all = sut.getValidRepresentations();
      assertEquals(4, all.size());
      assertThat(all, hasItem(valid((String) null)));
      assertThat(all, hasItem(valid(" ")));
      assertThat(all, hasItem(valid("\t")));
      assertThat(all, hasItem(valid(StringGenerator.gen(fill, 8077))));
    }
    { // invalid
      List<Representant<String>> all = sut.getInValidRepresentations();
      assertEquals(0, all.size());
    }
  }

  @Test
  public void whitespacesAreToTrimOnRequiredNoEmptyStringNotNull()
      throws Exception
  {
    sut.noEmptyStringCheck();
    sut.assumeWhitspacesEqualsAnEmptyString().setNullIsAValue(false);
    sut.required(false);
    { // valid
      List<Representant<String>> all = sut.getValidRepresentations();
      assertEquals(3, all.size());
      assertThat(all, not(hasItem(valid((String) null))));
      assertThat(all, hasItem(valid(" ")));
      assertThat(all, hasItem(valid("\t")));
      assertThat(all, hasItem(valid(StringGenerator.gen(fill, 8077))));
    }
    { // invalid
      List<Representant<String>> all = sut.getInValidRepresentations();
      assertEquals(0, all.size());
    }
  }
  
  @Test
  public void trimAndFilltoMaxLength() throws Exception
  {
    sut.trim(true);
    sut.maxLength(97);
    sut.noEmptyStringCheck();
    sut.setWhitspaceStrings("");
    sut.setNullIsAValue(false);
    { // valid
      List<Representant<String>> all = sut.getValidRepresentations();
      assertEquals(2, all.size());
      assertThat(all, hasItem(valid("")));
      for (Representant<String> representant : all)
      {
        assertThat(representant.getValue(), equalTo(representant.getValue().trim()));
        
      }
    }
    {
      List<Representant<String>> all = sut.getInValidRepresentations();
      assertEquals(1, all.size());
    }
    
  }
}
