package com.seitenbau.stu.data;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.seitenbau.stu.data.detail.Representant;
import com.seitenbau.stu.data.detail.Representant.RepresentantType;
import com.seitenbau.stu.data.impl.RepresentativeList;
import com.seitenbau.stu.data.specs.OfTypeTestBase;

public class RepresentativeListTest extends OfTypeTestBase
{
  @Test
  public void emptyList() throws Exception
  {
    RepresentativeList<String> sut = new RepresentativeList<String>(null);
    List<Representant<String>> all = sut.getList();
    assertEquals(0, all.size());
  }
  
  @Test
  public void noFilter() throws Exception
  {
    RepresentativeList<String> sut = new RepresentativeList<String>(null);
    sut.valid("valid");
    sut.inValid("invalid");
    List<Representant<String>> all = sut.getList();
    assertEquals(2, all.size());
    assertThat(all, hasItem(valid("valid")));
    assertThat(all, hasItem(invalid("invalid")));
  }
  
  @Test
  public void validFilter() throws Exception
  {
    RepresentativeList<String> sut = new RepresentativeList<String>(RepresentantType.VALID);
    sut.valid("valid");
    sut.inValid("invalid");
    List<Representant<String>> all = sut.getList();
    assertEquals(1, all.size());
    assertThat(all, hasItem(valid("valid")));
  }
  
  @Test
  public void inValidFilter() throws Exception
  {
    RepresentativeList<String> sut = new RepresentativeList<String>(RepresentantType.INVALID);
    sut.valid("valid");
    sut.inValid("invalid");
    List<Representant<String>> all = sut.getList();
    assertEquals(1, all.size());
    assertThat(all, hasItem(invalid("invalid")));
  }
}
