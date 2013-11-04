package com.seitenbau.stu.data.specs;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.seitenbau.stu.data.detail.Representant;
import com.seitenbau.stu.data.specs.OfTypeEMail;


public class OfTypeEMailTest extends OfTypeTestBase
{
  private OfTypeEMail sut = new OfTypeEMail();

  @Test
  public void withoutParametersValid() throws Exception
  {
    List<Representant<String>> all = sut.maxLength(10).getValidRepresentations();
    assertEquals(4, all.size());
    assertThat(all, hasItem(valid((String) null)));
    assertThat(all, hasItem(valid("x@x")));
    assertThat(all, hasItem(valid("fillfill@x")));
    assertThat(all, hasItem(valid("x@fillfill")));
  }
  
  @Test
  public void withoutParametersInValid() throws Exception
  {
    List<Representant<String>> all = sut.maxLength(10).getInValidRepresentations();
    assertEquals(4, all.size());
    assertThat(all, hasItem(invalid("@x")));
    assertThat(all, hasItem(invalid("x@")));
    assertThat(all, hasItem(invalid("fillfill@xx")));
    assertThat(all, hasItem(invalid("xx@fillfill")));
  }
  
  @Test
  public void withDefaultSubdomainValid() throws Exception
  {
    List<Representant<String>> all = sut.useSubdomain(true).maxLength(15)
        .getValidRepresentations();
    assertEquals(4, all.size());
    assertThat(all, hasItem(valid((String) null)));
    assertThat(all, hasItem(valid("x@" + OfTypeEMail._defaultSubdomain)));
    assertThat(all, hasItem(valid("fil@" + OfTypeEMail._defaultSubdomain)));
    assertThat(all, hasItem(valid("x@fi" + OfTypeEMail._defaultSubdomain)));
    
  }
  
  @Test
  public void withDefaultSubdomainInvalid() throws Exception
  {
    List<Representant<String>> all = sut.useSubdomain(true).maxLength(15)
        .getInValidRepresentations();
    assertEquals(4, all.size());
    assertThat(all, hasItem(invalid("@" + OfTypeEMail._defaultSubdomain)));
    assertThat(all, hasItem(invalid("x@")));
    assertThat(all, hasItem(invalid("fil@x" + OfTypeEMail._defaultSubdomain)));
    assertThat(all, hasItem(invalid("xx@fi" + OfTypeEMail._defaultSubdomain)));
  }
  
  @Test
  public void withCustomSubdomainValid() throws Exception
  {
    String mySubdomain = "test.sub.de";
    List<Representant<String>> all = sut.setSubdomain(mySubdomain).maxLength(15)
      .getValidRepresentations();
    assertEquals(4, all.size());
    assertThat(all, hasItem(valid((String) null)));
    assertThat(all, hasItem(valid("x@" + mySubdomain)));
    assertThat(all, hasItem(valid("fil@" + mySubdomain)));
    assertThat(all, hasItem(valid("x@fi" + mySubdomain)));
  }
  
  @Test
  public void withCustomSubdomainInvalid() throws Exception
  {
    String mySubdomain = "test.sub.de";
    List<Representant<String>> all = sut.setSubdomain(mySubdomain).maxLength(15)
      .getInValidRepresentations();
    assertEquals(4, all.size());
    assertThat(all, hasItem(invalid("@" + mySubdomain)));
    assertThat(all, hasItem(invalid("x@")));
    assertThat(all, hasItem(invalid("fil@x" + mySubdomain)));
    assertThat(all, hasItem(invalid("xx@fi" + mySubdomain)));
  }
  
  @Test
  public void nullIsNotAllowed_withoutParametersValid() throws Exception
  {
    List<Representant<String>> all = sut.setNullIsAValue(false).maxLength(10)
        .getValidRepresentations();
    assertEquals(3, all.size());
    assertThat(all, hasItem(valid("x@x")));
    assertThat(all, hasItem(valid("fillfill@x")));
    assertThat(all, hasItem(valid("x@fillfill")));
  }
  
  @Test
  public void nullIsNotAllowed_withoutParametersInvalid() throws Exception
  {
    List<Representant<String>> all = sut.setNullIsAValue(false).maxLength(10)
        .getInValidRepresentations();
    assertEquals(4, all.size());
    assertThat(all, hasItem(invalid("@x")));
    assertThat(all, hasItem(invalid("x@")));
    assertThat(all, hasItem(invalid("fillfill@xx")));
    assertThat(all, hasItem(invalid("xx@fillfill")));
  }
  
}
